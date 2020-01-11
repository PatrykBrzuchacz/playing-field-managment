import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { FriendsComponent } from '../components/friends/friends.component';
import { SharedModule } from '@app/shared/shared.module';
import { PlayingFieldComponent } from './components/playing-field/playing-field.component';
import { PlayingFieldRoutingModule } from './playing-field-routing.module';
import { ReservationDialogComponent } from './components/playing-field/reservation-dialog/reservation-dialog.component';
var PlayingFieldModule = /** @class */ (function () {
    function PlayingFieldModule() {
    }
    PlayingFieldModule = tslib_1.__decorate([
        NgModule({
            declarations: [FriendsComponent, PlayingFieldComponent, ReservationDialogComponent],
            imports: [
                PlayingFieldRoutingModule,
                SharedModule,
            ],
            entryComponents: [
                ReservationDialogComponent
            ]
        })
    ], PlayingFieldModule);
    return PlayingFieldModule;
}());
export { PlayingFieldModule };
//# sourceMappingURL=playing-field.module.js.map