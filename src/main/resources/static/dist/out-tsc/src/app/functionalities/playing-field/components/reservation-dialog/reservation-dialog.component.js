import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from "@angular/material/dialog";
import { MatchService } from "@app/shared/service/match.service";
import { FormGroup, FormControl } from "@angular/forms";
import { ReservationDto } from "@app/shared/model/playing-field";
import { ToastrService } from "ngx-toastr";
import { CodeDialogComponent } from "../code-dialog/code-dialog.component";
let ReservationDialogComponent = class ReservationDialogComponent {
    constructor(data, dialogRef, dialog, matchService, toastrService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.dialog = dialog;
        this.matchService = matchService;
        this.toastrService = toastrService;
    }
    ngOnInit() {
        this.initForm();
    }
    initForm() {
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
    }
    makeReservation() {
        const reservation = new ReservationDto(this.reservationForm.value.phoneNumber, this.reservationForm.value.email, this.reservationForm.value.lastName, this.reservationForm.value.isPrivate);
        this.matchService.bookPF(this.data.match.id, reservation).subscribe(val => {
            this.toastrService.success("Poprawnie zarezerwowano orlik, dziękujemy");
            this.dialog.open(CodeDialogComponent, {
                width: "500px",
                data: { code: val.code }
            });
            this.dialogRef.close({
                id: this.data.match.id,
                isBooked: true,
                isPrivate: this.reservationForm.value.isPrivate,
                code: val.code
            });
        }, () => {
            this.toastrService.error("Nie udało się zarezerwować orliku");
            this.dialogRef.close({ id: this.data.match.id, isBooked: false });
        });
    }
};
ReservationDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-reservation-dialog",
        templateUrl: "./reservation-dialog.component.html",
        styleUrls: ["./reservation-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        MatDialog,
        MatchService,
        ToastrService])
], ReservationDialogComponent);
export { ReservationDialogComponent };
//# sourceMappingURL=reservation-dialog.component.js.map