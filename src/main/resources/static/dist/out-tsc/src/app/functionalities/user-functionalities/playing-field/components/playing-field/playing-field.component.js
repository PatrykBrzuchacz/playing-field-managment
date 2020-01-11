import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { SearchDto } from "@app/shared/model/playing-field";
import { ActivatedRoute } from "@angular/router";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { MatDialog } from "@angular/material";
import { PlayingFieldService } from "@app/shared/service/playing-field.service";
import { EnlargeImageDialogComponent } from "@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component";
import { FormGroup, FormControl } from "@angular/forms";
import { DatePickerValidator } from "@app/shared/utils/datePickerValidator";
import * as moment from "moment";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { ReservationDialogComponent } from "./reservation-dialog/reservation-dialog.component";
var PlayingFieldComponent = /** @class */ (function () {
    function PlayingFieldComponent(activatedRoute, playingFieldService, dataSharingService, authService, userService, dialog) {
        this.activatedRoute = activatedRoute;
        this.playingFieldService = playingFieldService;
        this.dataSharingService = dataSharingService;
        this.authService = authService;
        this.userService = userService;
        this.dialog = dialog;
        this.minFromDate = new Date();
        this.maxToDate = new Date().setDate(2);
        this.spinner = true;
        this.displayedColumns = [
            "date",
            "fromTime",
            "toTime",
            "reservation",
            "size",
            "options"
        ];
    }
    PlayingFieldComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.params.subscribe(function (param) {
            _this.pfId = param["id"];
        });
        this.getLoggedUser();
        this.getSetup();
        this.initForm();
        this.searchMatches();
    };
    PlayingFieldComponent.prototype.makeReserveation = function (match) {
        var _this = this;
        var dialogRef = this.dialog.open(ReservationDialogComponent, {
            width: "400px",
            data: { match: match, loggedUser: this.loggedUser }
        });
        dialogRef.afterClosed().subscribe(function (val) {
            _this.matchesDto.find(function (match) { return (match.id = val.id); }).isBooked =
                val.isBooked;
        });
        console.log(match);
    };
    PlayingFieldComponent.prototype.checkMatch = function (match) { };
    PlayingFieldComponent.prototype.searchMatches = function () {
        var _this = this;
        this.playingFieldService
            .getMatches(this.pfId, new SearchDto(moment(this.searchForm.value.fromDate).format("YYYY-MM-DD"), moment(this.searchForm.value.toDate).format("YYYY-MM-DD"), this.searchForm.value.fromTime, this.searchForm.value.toTime, this.searchForm.value.showAll))
            .subscribe(function (val) {
            _this.matchesDto = val;
        });
    };
    PlayingFieldComponent.prototype.initForm = function () {
        var toDate = new Date();
        toDate.setDate(toDate.getDate() + 6);
        this.searchForm = new FormGroup({
            fromDate: new FormControl(new Date(), DatePickerValidator.fromDateValidator),
            toDate: new FormControl(toDate, DatePickerValidator.ToDateValidator),
            fromTime: new FormControl(""),
            toTime: new FormControl(""),
            showAll: new FormControl(false)
        });
    };
    PlayingFieldComponent.prototype.getSetup = function () {
        var _this = this;
        this.playingFieldService.getPFSetup(this.pfId).subscribe(function (val) {
            _this.playingFieldSetup = val;
            _this.dataSharingService.name.next(val.name);
        });
    };
    PlayingFieldComponent.prototype.enlarge = function () {
        this.dialog.open(EnlargeImageDialogComponent, {
            width: "80%",
            data: { image: this.playingFieldSetup.pfPhoto }
        });
    };
    PlayingFieldComponent.prototype.getLoggedUser = function () {
        var _this = this;
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(function (response) {
                _this.loggedUser = response;
            });
        }
    };
    PlayingFieldComponent = tslib_1.__decorate([
        Component({
            selector: "app-playing-field",
            templateUrl: "./playing-field.component.html",
            styleUrls: ["./playing-field.component.scss"]
        }),
        tslib_1.__metadata("design:paramtypes", [ActivatedRoute,
            PlayingFieldService,
            DataSharingService,
            AuthService,
            UserService,
            MatDialog])
    ], PlayingFieldComponent);
    return PlayingFieldComponent;
}());
export { PlayingFieldComponent };
//# sourceMappingURL=playing-field.component.js.map