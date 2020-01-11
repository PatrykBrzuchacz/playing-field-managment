import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { MatchService } from "@app/shared/service/match.service";
import { FormGroup, FormControl } from "@angular/forms";
import { ReservationDto } from "@app/shared/model/playing-field";
import { ToastrService } from 'ngx-toastr';
var ReservationDialogComponent = /** @class */ (function () {
    function ReservationDialogComponent(data, dialogRef, matchService, toastrService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.matchService = matchService;
        this.toastrService = toastrService;
    }
    ReservationDialogComponent.prototype.ngOnInit = function () {
        console.log(this.data);
        this.initForm();
    };
    ReservationDialogComponent.prototype.initForm = function () {
        if (this.data.loggedUser) {
            this.reservationForm = new FormGroup({
                phoneNumber: new FormControl(this.data.loggedUser.phoneNumber
                    ? this.data.loggedUser.phoneNumber
                    : ""),
                lastName: new FormControl(this.data.loggedUser.lastName ? this.data.loggedUser.lastName : ""),
                email: new FormControl(this.data.loggedUser.email ? this.data.loggedUser.email : ""),
                isPrivate: new FormControl(false)
            });
        }
        else {
            this.reservationForm = new FormGroup({
                phoneNumber: new FormControl(),
                lastName: new FormControl(),
                email: new FormControl(),
                isPrivate: new FormControl(false)
            });
        }
    };
    ReservationDialogComponent.prototype.makeReservation = function () {
        var _this = this;
        console.log("xd");
        var reservation = new ReservationDto(this.reservationForm.value.phoneNumber, this.reservationForm.value.email, this.reservationForm.value.lastName, this.reservationForm.value.isPrivate);
        this.matchService.bookPF(this.data.match.id, reservation).subscribe(function (val) {
            _this.toastrService.success("Poprawnie zarezerwowano orlik, dziękujemy");
            _this.dialogRef.close({ id: _this.data.match.id, isBooked: true });
        }, function (val) {
            _this.toastrService.error('Nie udało się zarezerwować orliku');
            _this.dialogRef.close({ id: _this.data.match.id, isBooked: false }),
            ;
        });
    };
    ReservationDialogComponent = tslib_1.__decorate([
        Component({
            selector: "app-reservation-dialog",
            templateUrl: "./reservation-dialog.component.html",
            styleUrls: ["./reservation-dialog.component.scss"]
        }),
        tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
        tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
            MatchService,
            ToastrService])
    ], ReservationDialogComponent);
    return ReservationDialogComponent;
}());
export { ReservationDialogComponent };
//# sourceMappingURL=reservation-dialog.component.js.map