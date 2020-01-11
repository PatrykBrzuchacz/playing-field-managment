import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FriendsComponent } from './components/friends/friends.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { SharedModule } from '@app/shared/shared.module';
var UserFunctionalitiesModule = /** @class */ (function () {
    function UserFunctionalitiesModule() {
    }
    UserFunctionalitiesModule = tslib_1.__decorate([
        NgModule({
            declarations: [FriendsComponent, ReservationComponent],
            imports: [
                SharedModule,
                CommonModule
            ]
        })
    ], UserFunctionalitiesModule);
    return UserFunctionalitiesModule;
}());
export { UserFunctionalitiesModule };
//# sourceMappingURL=user-functionalities.module.js.map