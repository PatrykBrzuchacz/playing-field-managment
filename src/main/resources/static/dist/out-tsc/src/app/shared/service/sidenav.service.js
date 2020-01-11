import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
var SidenavService = /** @class */ (function () {
    function SidenavService() {
        this.resource = new Subject();
        this.sidenavOpened = this.resource.asObservable();
        this.locationResource = new Subject();
        this.selectedLocation = this.locationResource.asObservable();
    }
    SidenavService.prototype.setSidenavOpened = function (opened) {
        this.resource.next(opened);
    };
    SidenavService.prototype.setSelectedLocation = function (location) {
        this.locationResource.next(location);
    };
    SidenavService = tslib_1.__decorate([
        Injectable({
            providedIn: 'root'
        })
    ], SidenavService);
    return SidenavService;
}());
export { SidenavService };
//# sourceMappingURL=sidenav.service.js.map