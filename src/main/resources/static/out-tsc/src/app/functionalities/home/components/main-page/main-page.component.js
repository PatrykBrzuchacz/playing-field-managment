import * as tslib_1 from "tslib";
import { AgmMap, MapsAPILoader } from "@agm/core";
import { Component, ViewChild, ViewEncapsulation } from "@angular/core";
import { of } from "rxjs";
import { ToastrService } from "ngx-toastr";
import { GoogleService } from "@app/shared/service/google.service";
import { GeoLocationService } from "@app/shared/service/geo-location.service";
import { AuthService } from "@app/core/service";
import { LocationService } from "@app/shared/service/location.service";
import { FormControl } from "@angular/forms";
import { switchMap, map, catchError, tap } from "rxjs/operators";
import { MatDialog } from "@angular/material/dialog";
import { UserService } from "@app/shared/service/user.service";
import { AssignPlayingFieldAndRegisterDialogComponent } from "../assign-playing-field-and-register-dialog/assign-playing-field-and-register-dialog.component";
let MainPageComponent = class MainPageComponent {
    constructor(googleService, geoLocationService, authService, toastr, mapsApiLoader, locationService, userService, dialog) {
        this.googleService = googleService;
        this.geoLocationService = geoLocationService;
        this.authService = authService;
        this.toastr = toastr;
        this.mapsApiLoader = mapsApiLoader;
        this.locationService = locationService;
        this.userService = userService;
        this.dialog = dialog;
        this.selectedLocationControl = new FormControl();
        this.userLocation = {
            lat: 0,
            lng: 0,
            zoom: 13
        };
        this.selectedLocation2 = {
            lat: 50.0,
            lng: 20.0,
            zoom: 13
        };
        this.customerLabel = "Tu jesteś";
        this.customerMapIcon = "assets/icons/customer-map-marker.svg";
        this.shopMapIcon = "assets/icons/shop-map-marker.svg";
        this.registeredIcon = "assets/icons/registered-map-marker.svg";
        this.ownerPfIcon = "assets/icons/owner-pf-marker.svg";
        this.playingFieldList = [];
        this.setSelectedLocation = (location) => {
            if (location) {
                this.selectedLocation2 = {
                    lat: location.lat,
                    lng: location.lng,
                    zoom: 13
                };
                this.findPlaces(this.selectedLocation2.lat, this.selectedLocation2.lng);
            }
        };
        this.handleSuccessResponse = (response) => {
            console.log(response);
            this.playingFieldList = response;
            if (!this.playingFieldList.length) {
                this.toastr.info("Nie znaleziono żadnych orlików w tym miejscu!");
            }
        };
        this.handleSelectedLocationChange = () => {
            return this.selectedLocationControl.valueChanges.pipe(switchMap((address) => {
                return this.geoLocationService.getPositions(address);
            }), map((data) => {
                return data && data;
            }), catchError((error) => {
                return of([]);
            }), tap((data) => {
                this.searchedLocations = data;
            }));
        };
        this.mapsApiLoader.load().then(() => {
            this.geoCoder = new google.maps.Geocoder();
        });
    }
    ngAfterViewInit() {
        this.getLoggedUser();
    }
    ngOnInit() {
        this.subscription = this.subscribeUserSelectedLocation();
        this.locations = this.handleSelectedLocationChange();
        this.selectedLocation = this.userLocation;
        if (navigator) {
            navigator.geolocation.getCurrentPosition(pos => {
                this.userLocation.lng = pos.coords.longitude;
                this.userLocation.lat = pos.coords.latitude;
                this.userLocationClone = JSON.parse(JSON.stringify(this.userLocation));
                this.findPlaces(pos.coords.latitude, pos.coords.longitude);
            });
        }
    }
    markerClick(infoWindow) {
        if (infoWindow == this.lastSelectedInfoWindow) {
            return;
        }
        if (this.lastSelectedInfoWindow != null) {
            try {
                this.lastSelectedInfoWindow.close();
            }
            catch (_a) { } //in case if you reload your markers
        }
        this.lastSelectedInfoWindow = infoWindow;
    }
    mapReady(map) {
        map.addListener("dragend", () => {
            this.findPlaces(this.selectedLocation2.lat, this.selectedLocation2.lng);
        });
    }
    centerChange(e) {
        this.selectedLocation2.lat = e.lat;
        this.selectedLocation2.lng = e.lng;
    }
    subscribeUserSelectedLocation() {
        return this.locationService.selectedLocation.subscribe(this.setSelectedLocation);
    }
    findPlaces(lat, lng) {
        this.googleService
            .getGooglePlaces(lat, lng)
            .subscribe(this.handleSuccessResponse);
    }
    setLocation($event) {
        const location = $event.option.value;
        const index = this.searchedLocations.findIndex(elem => elem.formatted_address === location);
        if (index !== -1) {
            const loc = this.searchedLocations[index];
            const selectedLocation = {
                lat: loc.geometry.location.lat(),
                lng: loc.geometry.location.lng(),
                zoom: 13
            };
            this.locationService.setSelectedLocation(selectedLocation);
            this.userLocation = selectedLocation;
            //this.selectedLocation = selectedLocation;
        }
    }
    getIcon(pf) {
        if (pf.userDto && this.loggedUser) {
            if (pf.userDto.id === this.loggedUser.id) {
                return this.ownerPfIcon;
            }
        }
        if (pf.registered === true && pf.active) {
            return this.registeredIcon;
        }
        else {
            return this.shopMapIcon;
        }
    }
    getMarkerLabel(name) {
        return {
            color: "red",
            text: name
        };
    }
    assignPFAndRegisterDialog(pf) {
        this.dialog.open(AssignPlayingFieldAndRegisterDialogComponent, {
            width: "350px",
            data: { playingField: pf }
        });
    }
    isLogged() {
        return this.authService.isLogged();
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(response => {
                this.loggedUser = response;
            });
        }
    }
    resetLocation() {
        this.userLocation = this.userLocationClone;
        this.findPlaces(this.userLocation.lat, this.userLocation.lng);
    }
    isPFOwner(pf) {
        if (pf.registered) {
            if (!pf.userDto || !pf.userDto.id || !this.loggedUser) {
                return false;
            }
            return pf.userDto.id === this.loggedUser.id;
        }
        else
            return false;
    }
};
tslib_1.__decorate([
    ViewChild(AgmMap, { static: false }),
    tslib_1.__metadata("design:type", AgmMap)
], MainPageComponent.prototype, "map", void 0);
MainPageComponent = tslib_1.__decorate([
    Component({
        selector: "app-main-page",
        templateUrl: "./main-page.component.html",
        styleUrls: ["./main-page.component.scss"],
        encapsulation: ViewEncapsulation.None
    }),
    tslib_1.__metadata("design:paramtypes", [GoogleService,
        GeoLocationService,
        AuthService,
        ToastrService,
        MapsAPILoader,
        LocationService,
        UserService,
        MatDialog])
], MainPageComponent);
export { MainPageComponent };
//# sourceMappingURL=main-page.component.js.map