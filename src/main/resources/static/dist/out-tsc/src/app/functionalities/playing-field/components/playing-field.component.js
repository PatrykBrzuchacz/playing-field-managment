import * as tslib_1 from "tslib";
import { Component, ViewChild } from "@angular/core";
import { SearchDto, SearchParams } from "@app/shared/model/playing-field";
import { ActivatedRoute, Router } from "@angular/router";
import { MatchService } from "@app/shared/service/match.service";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { MatDialog } from "@angular/material/dialog";
import { ToastrService } from "ngx-toastr";
import { PlayingFieldService } from "@app/shared/service/playing-field.service";
import { EnlargeImageDialogComponent } from "@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component";
import { FormGroup, FormControl } from "@angular/forms";
import { DatePickerValidator } from "@app/shared/utils/datePickerValidator";
import * as moment from "moment";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { ReservationDialogComponent } from "./reservation-dialog/reservation-dialog.component";
import { TeamsDialogComponent } from "./teams-dialog/teams-dialog.component";
import { MatTable, MatPaginator, MatSort } from "@angular/material";
import { BanService } from "@app/shared/service/ban.service";
import { WorkerBanDialogComponent } from "@app/shared/components/worker-ban-dialog/worker-ban-dialog.component";
import { startWith, switchMap, map, catchError } from "rxjs/operators";
import { of, merge } from "rxjs";
import { RateService } from "@app/shared/service/rate.service";
let PlayingFieldComponent = class PlayingFieldComponent {
    constructor(activatedRoute, router, playingFieldService, dataSharingService, authService, userService, matchService, banService, rateService, toastrService, dialog) {
        this.activatedRoute = activatedRoute;
        this.router = router;
        this.playingFieldService = playingFieldService;
        this.dataSharingService = dataSharingService;
        this.authService = authService;
        this.userService = userService;
        this.matchService = matchService;
        this.banService = banService;
        this.rateService = rateService;
        this.toastrService = toastrService;
        this.dialog = dialog;
        this.starValue = 1;
        this.isRatePossible = false;
        this.minFromDate = new Date();
        this.maxToDate = new Date().setDate(2);
        this.spinner = true;
        this.isBanned = false;
        this.displayedColumns = [
            "date",
            "fromTime",
            "reservation",
            "private",
            "size",
            "options"
        ];
    }
    ngOnInit() {
        this.activatedRoute.params.subscribe(param => {
            this.pfId = param["id"];
            this.initForm();
            this.handlePFTableChange();
        });
        if (this.pfId) {
            this.getLoggedUser();
            this.getSetup();
            this.getRate();
        }
    }
    initForm() {
        const toDate = new Date();
        const searchParams = JSON.parse(localStorage.getItem("searchParams"));
        // let currentTime: any;
        // currentTime = new Date().getHours() + ":" + (new Date().getMinutes()<10?'0':'' + new Date().getMinutes());
        // toDate.setDate(toDate.getDate() + 6);
        if (searchParams) {
            // if (searchParams.searchDto.fromTime < currentTime) {
            //   searchParams.searchDto.fromTime = currentTime;
            // }
            this.searchForm = new FormGroup({
                fromDate: new FormControl(searchParams.searchDto.fromDate, DatePickerValidator.fromDateValidator),
                toDate: new FormControl(searchParams.searchDto.toDate, DatePickerValidator.ToDateValidator),
                fromTime: new FormControl(searchParams.searchDto.fromTime),
                toTime: new FormControl(searchParams.searchDto.toTime),
                reserved: new FormControl(1),
                showPrivate: new FormControl(true),
                showFull: new FormControl(false)
            });
        }
        else {
            this.searchForm = new FormGroup({
                fromDate: new FormControl(new Date(), DatePickerValidator.fromDateValidator),
                toDate: new FormControl(toDate, DatePickerValidator.ToDateValidator),
                fromTime: new FormControl(""),
                toTime: new FormControl(""),
                reserved: new FormControl(1),
                showPrivate: new FormControl(false),
                showFull: new FormControl(false)
            });
        }
    }
    makeReserveation(match) {
        if (!this.isBanned) {
            const dialogRef = this.dialog.open(ReservationDialogComponent, {
                width: "400px",
                data: { match: match, loggedUser: this.loggedUser }
            });
            dialogRef.afterClosed().subscribe(val => {
                if (val) {
                    console.log(val);
                    const bookedMatch = this.matchesDto.find(match => match.id === val.id);
                    bookedMatch.isBooked = val.isBooked;
                    bookedMatch.ownerId = this.loggedUser.id;
                    bookedMatch.ownerUsername = this.loggedUser.username;
                    bookedMatch.size = 1;
                    bookedMatch.isPrivate = val.isPrivate;
                    bookedMatch.code = val.code;
                    this.matchWithLocationDtoTable.renderRows();
                }
            });
        }
    }
    checkMatch(match) {
        const dialogRef = this.dialog.open(TeamsDialogComponent, {
            width: "800px",
            data: {
                isPrivate: match.isPrivate,
                matchId: match.id,
                ownerId: match.ownerId,
                pfId: this.pfId,
                editable: match.editable
            },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            match.size = val;
        });
    }
    getSetup() {
        this.playingFieldService.getPFSetup(this.pfId).subscribe(val => {
            this.playingFieldSetup = val;
            this.dataSharingService.name.next(val.name);
        });
    }
    unbook(match) {
        this.matchService.unbookPF(match.id).subscribe(val => {
            match.isBooked = false;
            match.size = 0;
            match.isPrivate = false;
            match.ownerId = null;
            match.ownerUsername = null;
            this.matchWithLocationDtoTable.renderRows();
        });
    }
    enlarge() {
        this.dialog.open(EnlargeImageDialogComponent, {
            width: "80%",
            data: { image: this.playingFieldSetup.pfPhoto },
            panelClass: "custom-enlarge-dialog-container"
        });
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(response => {
                this.loggedUser = response;
                this.banService
                    .isUserBanned(this.loggedUser.id, this.pfId)
                    .subscribe(val => {
                    this.isBanned = val;
                });
                this.rateService.existsRateByLoggedUser(this.pfId).subscribe(val => {
                    this.isRatePossible = !val;
                });
            });
        }
    }
    goToUserProfile(id) {
        this.dataSharingService.changeUser(id.toString());
        this.router.navigate(["users/" + id]);
    }
    getRate() {
        this.rateService.getRate(this.pfId).subscribe(val => {
            this.rate = val;
        });
    }
    addRate() {
        if (this.starValue > 0) {
            this.rateService.addRate(this.pfId, this.starValue).subscribe(val => {
                this.rate.rate = val;
                this.rate.numberOfVotes += 1;
                this.isRatePossible = false;
            });
        }
        else {
            this.toastrService.error("Przed oceną wybierz na ile oceniasz orlik");
        }
    }
    banUser(userId) {
        const dialogRef = this.dialog.open(WorkerBanDialogComponent, {
            width: "30%",
            data: { pfId: this.pfId, userId: userId },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.matchesDto.forEach(match => {
                    if (match.ownerId === userId) {
                        match.isBooked = false;
                        match.isPrivate = false;
                        match.ownerId = null;
                        match.ownerUsername = "";
                    }
                });
                this.matchWithLocationDtoTable.renderRows();
            }
        });
    }
    handlePFTableChange() {
        const searchDto = new SearchDto(moment(this.searchForm.value.fromDate).format("YYYY-MM-DD"), moment(this.searchForm.value.toDate).format("YYYY-MM-DD"), this.searchForm.value.fromTime, this.searchForm.value.toTime, this.searchForm.value.showPrivate, this.searchForm.value.showFull, this.searchForm.value.reserved);
        if (localStorage.getItem("searchParams")) {
            const searchParams = JSON.parse(localStorage.getItem("searchParams"));
            searchParams.searchDto = searchDto;
            localStorage.setItem("searchParams", JSON.stringify(searchParams));
        }
        else {
            const searchParams = new SearchParams(null, null, null, searchDto);
            localStorage.setItem("searchParams", JSON.stringify(searchParams));
        }
        this.matchPaginator.pageSize = 10;
        merge(this.sort.sortChange, this.matchPaginator.page, this.matchPaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: [`matchFromDate,asc`, `matchFromTime,asc`],
                page: this.matchPaginator.pageIndex + "",
                size: this.matchPaginator.pageSize + ""
            };
            return this.playingFieldService.getMatches(this.pfId, searchDto, params);
        }), map((data) => {
            this.pfLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => (this.matchesDto = data));
    }
    setRate(value) {
        this.starValue = value;
    }
};
tslib_1.__decorate([
    ViewChild("availabilityTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], PlayingFieldComponent.prototype, "matchWithLocationDtoTable", void 0);
tslib_1.__decorate([
    ViewChild(MatPaginator, { static: true }),
    tslib_1.__metadata("design:type", MatPaginator)
], PlayingFieldComponent.prototype, "matchPaginator", void 0);
tslib_1.__decorate([
    ViewChild(MatSort, { static: true }),
    tslib_1.__metadata("design:type", MatSort)
], PlayingFieldComponent.prototype, "sort", void 0);
PlayingFieldComponent = tslib_1.__decorate([
    Component({
        selector: "app-playing-field",
        templateUrl: "./playing-field.component.html",
        styleUrls: ["./playing-field.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [ActivatedRoute,
        Router,
        PlayingFieldService,
        DataSharingService,
        AuthService,
        UserService,
        MatchService,
        BanService,
        RateService,
        ToastrService,
        MatDialog])
], PlayingFieldComponent);
export { PlayingFieldComponent };
//# sourceMappingURL=playing-field.component.js.map