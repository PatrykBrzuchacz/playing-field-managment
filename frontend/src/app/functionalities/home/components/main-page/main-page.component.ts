import { AgmMap, MapsAPILoader } from '@agm/core';
import { Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation, Input } from '@angular/core';

import { Subscription, Observable, of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { GoogleLocation } from '@app/shared/model/google-location';
import { GooglePlaceMap } from '@app/shared/model/google-map';
import { GoogleService } from '@app/shared/service/google.service';
import { GeoLocationService } from '@app/shared/service/geo-location.service';
import { AuthService } from '@app/core/service';
import { DialogService } from '@app/shared/service/dialog.service';
import { SidenavService } from '@app/shared/service/sidenav.service';
import { FormControl } from '@angular/forms';
import { delay, startWith, switchMap, map, catchError, tap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MainPageComponent implements OnInit, OnDestroy {

  selectedUserLocation: GoogleLocation = {
    lat: 52.00,
    lng: 22.00,
    zoom: 13
  };
  userLocation: GoogleLocation = {
    lat: 0,
    lng: 0,
    zoom: 13
  };
  selectedLocationControl = new FormControl();
  locations: Observable<any[]>;
  searchedLocations: any[];
  @ViewChild(AgmMap) map: AgmMap;

  geo = navigator.geolocation;
  customerLabel = 'Tu jesteś';
  customerMapIcon = 'assets/icons/customer-map-marker.svg';
  shopMapIcon = 'assets/icons/shop-map-marker.svg';
  registeredIcon = 'assets/icons/registered-map-maker.svg';
  googlePlace: GooglePlaceMap[];
  isRegistered: boolean;

  subscription: Subscription;
  public geoCoder: any;
  userQuery = '';
  @Input()
  isOpen: boolean;
  private sidenavOpened = false;

  constructor(private googleService: GoogleService, private dialogService: DialogService,
              private geoLocationService: GeoLocationService, private authService: AuthService,
              private sidenavService: SidenavService,
              private toastr: ToastrService,
              private mapsApiLoader: MapsAPILoader,
              private sideNavService: SidenavService) {


      this.mapsApiLoader.load().then(() => {
        this.geoCoder = new google.maps.Geocoder();
      });

      if (navigator) {
    navigator.geolocation.getCurrentPosition( pos => {
        this.userLocation.lng = +pos.coords.longitude;
        this.userLocation.lat = +pos.coords.latitude;
      });
    }
  }

  ngOnInit() {
    this.initUserLocation();
    this.subscription = this.subscribeUserSelectedLocation();
    this.locations = this.handleSelectedLocationChange();
  }

  private subscribeUserSelectedLocation(): Subscription {
    return this.sidenavService.selectedLocation
      .subscribe(this.setSelectedLocation);
  }

  private setSelectedLocation = (location: GoogleLocation) => {
    if (location) {
      this.selectedUserLocation = {
        lat: location.lat,
        lng: location.lng,
        zoom: 15
      };
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  // sidenavToggle() {
  //   this.sidenavOpened = !this.sidenavOpened;
  //   this.sidenavService.setSidenavOpened(this.sidenavOpened);
  // }

  findPlacesByCity() {
    console.log(this.selectedUserLocation.lat + 'dupa');
    this.googleService.getGooglePlaces({
      query: this.selectedLocationControl.value
    })
    .subscribe(this.handleSuccessResponse);
  }

  private handleSuccessResponse = (response: GooglePlaceMap[]) => {
    console.log(response[0].name);
    this.googlePlace = response;
    if (!this.googlePlace.length) {
      this.toastr.info('Nie znaleziono żadnych miejsc o podanym kryterium');
    }
  }

  private initUserLocation() {
    this.geoLocationService.getCurrentPosition()
      .subscribe((position: Position) => {
        this.selectedUserLocation.lat = position.coords.latitude;
        this.selectedUserLocation.lng = position.coords.longitude;
      });
  }

  getIcon(isRegistered: boolean) {
    if (isRegistered === true) {
      return this.registeredIcon;
    } else {
      return this.shopMapIcon;
    }
  }

  getMarkerLabel(name: string) {
    return {
      color: 'red',
      text: name
    };
  }

  reservationDialog(venueId: string) {
    this.dialogService.openReservationDialog(venueId);
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }
  setCurrentLocation() {
    const userLocation: GoogleLocation = {
      lat: this.userLocation.lat,
      lng: this.userLocation.lng,
      zoom: 15
    };
    this.sideNavService.setSelectedLocation(userLocation);
  }

  private handleSelectedLocationChange = (): Observable<any[]> => {
    return this.selectedLocationControl.valueChanges
      .pipe(
        delay(50),
        startWith(''),
        switchMap((address: string) => {
          return this.geoLocationService.getPositions(address);
        }),
        map((data: any) => {
          return data && data;
        }),
        catchError((error: any) => {
          return of([]);
        }),
        tap((data: any[]) => {
          this.searchedLocations = data;
        })
      );
  }

  setLocation($event: MatAutocompleteSelectedEvent) {
    const location: string = $event.option.value;
    const index = this.searchedLocations.findIndex(elem => elem.formatted_address === location);

    if (index !== -1) {
      const loc: google.maps.GeocoderResult = this.searchedLocations[index];
      const selectedLocation: GoogleLocation = {
        lat: loc.geometry.location.lat(),
        lng: loc.geometry.location.lng(),
        zoom: 15
      };
      this.sideNavService.setSelectedLocation(selectedLocation);
    }
  }
}
