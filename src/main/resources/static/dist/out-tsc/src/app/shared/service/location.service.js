import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
let LocationService = class LocationService {
    constructor() {
        this.locationResource = new Subject();
        this.selectedLocation = this.locationResource.asObservable();
    }
    setSelectedLocation(location) {
        this.locationResource.next(location);
    }
};
LocationService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], LocationService);
export { LocationService };
//# sourceMappingURL=location.service.js.map