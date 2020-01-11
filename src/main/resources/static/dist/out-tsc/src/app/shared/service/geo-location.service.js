import * as tslib_1 from "tslib";
import { MapsAPILoader } from '@agm/core';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
let GeoLocationService = class GeoLocationService {
    constructor(mapsApiLoader, toastr) {
        this.mapsApiLoader = mapsApiLoader;
        this.toastr = toastr;
        this.getCurrentPosition = () => {
            return Observable.create((observer) => {
                navigator.geolocation.getCurrentPosition((pos) => {
                    observer.next(pos);
                    observer.complete();
                }, this.handleError, {
                    enableHighAccuracy: true
                });
            });
        };
        this.handleError = (error) => {
            this.toastr.error(error.message, 'Błąd!');
        };
        this.getPositions = (address) => {
            return Observable.create((observer) => {
                this.geoCoder.geocode({ 'address': address }, function (results, status) {
                    observer.next(results);
                    observer.complete();
                });
            });
        };
        this.mapsApiLoader.load().then(() => {
            this.geoCoder = new google.maps.Geocoder();
        });
    }
};
GeoLocationService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [MapsAPILoader, ToastrService])
], GeoLocationService);
export { GeoLocationService };
//# sourceMappingURL=geo-location.service.js.map