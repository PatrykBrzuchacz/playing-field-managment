import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { Router } from '@angular/router';
var FriendsComponent = /** @class */ (function () {
    function FriendsComponent(router) {
        this.router = router;
    }
    FriendsComponent.prototype.ngOnInit = function () {
        console.log("xd");
    };
    FriendsComponent = tslib_1.__decorate([
        Component({
            selector: 'app-friends',
            templateUrl: './friends.component.html',
            styleUrls: ['./friends.component.scss']
        }),
        tslib_1.__metadata("design:paramtypes", [Router])
    ], FriendsComponent);
    return FriendsComponent;
}());
export { FriendsComponent };
//# sourceMappingURL=friends.component.js.map