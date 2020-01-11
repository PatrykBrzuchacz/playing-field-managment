import * as tslib_1 from "tslib";
import { Component, ViewChild } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { DatePickerValidator } from "@app/shared/utils/datePickerValidator";
import { MatDialog } from "@angular/material/dialog";
import { MatTable } from "@angular/material/table";
import { LocationService } from "@app/shared/service/location.service";
import { of, merge } from "rxjs";
import { switchMap, map, catchError, tap, startWith } from "rxjs/operators";
import { GeoLocationService } from "@app/shared/service/geo-location.service";
import { MapsAPILoader, AgmMap } from "@agm/core";
import * as moment from "moment";
import { SearchDto, SearchParams } from "@app/shared/model/playing-field";
import { MatchService } from "@app/shared/service/match.service";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { UserService } from "@app/shared/service/user.service";
import { AuthService } from "@app/core/service";
import { TeamsDialogComponent } from "@app/functionalities/playing-field/components/teams-dialog/teams-dialog.component";
import { TeamService } from "@app/shared/service/team.service";
import { ReservationDialogComponent } from "@app/functionalities/playing-field/components/reservation-dialog/reservation-dialog.component";
import { MatPaginator, MatSort } from "@angular/material";
import { ToastrService } from "ngx-toastr";
import { Router } from '@angular/router';
let SearchMatchComponent = class SearchMatchComponent {
    constructor(locationService, geoLocationService, mapsApiLoader, matchService, userService, dialog, authService, teamService, dataSharingService, toastrService, router) {
        this.locationService = locationService;
        this.geoLocationService = geoLocationService;
        this.mapsApiLoader = mapsApiLoader;
        this.matchService = matchService;
        this.userService = userService;
        this.dialog = dialog;
        this.authService = authService;
        this.teamService = teamService;
        this.dataSharingService = dataSharingService;
        this.toastrService = toastrService;
        this.router = router;
        this.minFromDate = new Date();
        this.maxToDate = new Date().setDate(2);
        this.userLocation = {
            lat: 0,
            lng: 0,
            zoom: 13
        };
        this.displayedColumns = [
            "name",
            "address",
            "matchFrom",
            "matchTo",
            "private",
            "reserved",
            "size",
            "rate",
            "options"
        ];
        this.setSelectedLocation = (location) => {
            if (location) {
                this.selectedLocation2 = {
                    lat: location.lat,
                    lng: location.lng,
                    zoom: 13
                };
            }
        };
        this.handleSelectedLocationChange = () => {
            return this.searchForm.controls.city.valueChanges.pipe(switchMap((address) => {
                return this.geoLocationService.getPositions(address);
            }), map((data) => {
                return data && data;
            }), catchError((error) => {
                return of([]);
            }), tap((data) => {
                console.log(data);
                this.searchedLocations = data;
            }));
        };
        this.mapsApiLoader.load().then(() => {
            this.geoCoder = new google.maps.Geocoder();
        });
    }
    ngOnInit() {
        this.initForm();
        this.getLoggedUser();
        this.subscription = this.subscribeUserSelectedLocation();
        this.locations = this.handleSelectedLocationChange();
        this.getUserLocation();
    }
    initForm() {
        const searchParams = JSON.parse(localStorage.getItem("searchParams"));
        if (searchParams) {
            this.searchForm = new FormGroup({
                fromDate: new FormControl(searchParams.searchDto.fromDate, DatePickerValidator.fromDateValidator),
                toDate: new FormControl(searchParams.searchDto.toDate, DatePickerValidator.ToDateValidator),
                fromTime: new FormControl(searchParams.searchDto.fromTime),
                toTime: new FormControl(searchParams.searchDto.toTime),
                showPrivate: new FormControl(searchParams.searchDto.showPrivate),
                showFull: new FormControl(searchParams.searchDto.showFull),
                reserved: new FormControl(searchParams.searchDto.reserved),
                myLocalization: new FormControl(true),
                city: new FormControl({ value: "", disabled: "true" }),
                rangeInKm: new FormControl(searchParams.rangeInKm)
            });
        }
        else {
            const defaultToDate = moment(new Date(), "YYYY-MM-DD").add(5, "days");
            this.searchForm = new FormGroup({
                fromDate: new FormControl(new Date(), DatePickerValidator.fromDateValidator),
                toDate: new FormControl(defaultToDate, DatePickerValidator.ToDateValidator),
                fromTime: new FormControl("14:00"),
                toTime: new FormControl("18:00"),
                showPrivate: new FormControl(false),
                showFull: new FormControl(false),
                reserved: new FormControl(1),
                myLocalization: new FormControl(true),
                city: new FormControl({ value: "", disabled: "true" }),
                rangeInKm: new FormControl(1)
            });
        }
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
            this.selectedLocation = selectedLocation;
        }
    }
    getUserLocation() {
        if (navigator) {
            navigator.geolocation.getCurrentPosition(pos => {
                this.userLocation.lng = pos.coords.longitude;
                this.userLocation.lat = pos.coords.latitude;
                this.handlePFTableChange();
            });
        }
    }
    subscribeUserSelectedLocation() {
        return this.locationService.selectedLocation.subscribe(this.setSelectedLocation);
    }
    setMyLocalization() {
        if (!this.searchForm.controls.myLocalization.value) {
            this.searchForm.controls.city.disable();
        }
        else {
            this.searchForm.controls.city.enable();
        }
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(response => {
                this.loggedUser = response;
            });
        }
    }
    unbook(match) {
        this.matchService.unbookPF(match.id).subscribe(val => {
            match.isBooked = false;
            match.size = 0;
            match.isPrivate = false;
            match.ownerId = null;
            match.ownerUsername = null;
        });
    }
    checkMatch(match) {
        const dialogRef = this.dialog.open(TeamsDialogComponent, {
            width: "800px",
            data: {
                teamsDto: this.teamService.getTeamsByMatch(match.id),
                isPrivate: match.isPrivate,
                matchId: match.id,
                pfId: match.pfId,
                editable: match.editable,
                ownerId: match.ownerId
            }
        });
        dialogRef.afterClosed().subscribe(val => {
            match.size = val;
        });
    }
    makeReserveation(match) {
        if (!match.isBanned) {
            const dialogRef = this.dialog.open(ReservationDialogComponent, {
                width: "400px",
                data: { match: match, loggedUser: this.loggedUser }
            });
            dialogRef.afterClosed().subscribe(val => {
                if (val) {
                    match.isPrivate = val.isPrivate;
                    match.isBooked = true;
                    match.size = 1;
                    match.ownerUsername = this.loggedUser.username;
                    match.ownerId = this.loggedUser.username;
                    this.matchWithLocationDtoTable.renderRows();
                }
            });
        }
    }
    handlePFTableChange() {
        const searchDto = new SearchDto(moment(this.searchForm.value.fromDate).format("YYYY-MM-DD"), moment(this.searchForm.value.toDate).format("YYYY-MM-DD"), this.searchForm.value.fromTime, this.searchForm.value.toTime, this.searchForm.value.showPrivate, this.searchForm.value.showFull, this.searchForm.value.reserved);
        let searchParams;
        if (this.searchForm.controls.myLocalization.value) {
            searchParams = new SearchParams(this.userLocation.lat, this.userLocation.lng, this.searchForm.value.rangeInKm, searchDto);
        }
        else {
            if (this.searchForm.controls.city.value) {
                this.toastrService.error("Uzupełnij poprawnie miejscowość wybierając go z listy po kliknięciu w pole miejscowość i rozpoczęciu wpisywania miejscowości");
            }
            if (this.selectedLocation) {
                this.toastrService.error("Uzupełnij pole miejscowość lub wybierz swoją lokalizację");
            }
            else {
                searchParams = new SearchParams(this.selectedLocation.lat, this.selectedLocation.lng, this.searchForm.value.rangeInKm, searchDto);
            }
        }
        localStorage.setItem("searchParams", JSON.stringify(searchParams));
        if (searchParams) {
            this.matchPaginator.pageSize = 10;
            merge(this.sort.sortChange, this.matchPaginator.page, this.matchPaginator.pageSize)
                .pipe(startWith({}), switchMap(() => {
                const params = {
                    sort: [`matchFromDate,asc`, `matchFromTime,asc`],
                    page: this.matchPaginator.pageIndex + "",
                    size: this.matchPaginator.pageSize + ""
                };
                return this.matchService.getMatchesByLocation(searchParams, params);
            }), map((data) => {
                this.matchLength = data.totalElements;
                return data.content;
            }), catchError(() => {
                return of([]);
            }))
                .subscribe(data => (this.matchesWithLocationDto = data));
        }
    }
    goToUserProfile(id) {
        this.dataSharingService.changeUser(id.toString());
        this.router.navigate(["users/" + id]);
    }
};
tslib_1.__decorate([
    ViewChild(MatTable, { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], SearchMatchComponent.prototype, "matchWithLocationDtoTable", void 0);
tslib_1.__decorate([
    ViewChild(MatPaginator, { static: true }),
    tslib_1.__metadata("design:type", MatPaginator)
], SearchMatchComponent.prototype, "matchPaginator", void 0);
tslib_1.__decorate([
    ViewChild(MatSort, { static: false }),
    tslib_1.__metadata("design:type", MatSort)
], SearchMatchComponent.prototype, "sort", void 0);
tslib_1.__decorate([
    ViewChild(AgmMap, { static: true }),
    tslib_1.__metadata("design:type", AgmMap)
], SearchMatchComponent.prototype, "map", void 0);
SearchMatchComponent = tslib_1.__decorate([
    Component({
        selector: "app-search-match",
        templateUrl: "./search-match.component.html",
        styleUrls: ["./search-match.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [LocationService,
        GeoLocationService,
        MapsAPILoader,
        MatchService,
        UserService,
        MatDialog,
        AuthService,
        TeamService,
        DataSharingService,
        ToastrService,
        Router])
], SearchMatchComponent);
export { SearchMatchComponent };
//# sourceMappingURL=search-match.component.js.map