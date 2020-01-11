import * as tslib_1 from "tslib";
import { Component, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTable } from "@angular/material/table";
import { WorkerPFDatePickerComponent } from "../worker-pfdate-picker/worker-pfdate-picker.component";
import { ActivatedRoute, Router } from "@angular/router";
import { MatchService } from "@app/shared/service/match.service";
import { PlayingFieldService } from "@app/shared/service/playing-field.service";
import { WorkerPFSetupDialogComponent } from "../worker-pfsetup-dialog/worker-pfsetup-dialog.component";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { ToastrService } from "ngx-toastr";
import { EnlargeImageDialogComponent } from "@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component";
import { DomSanitizer } from "@angular/platform-browser";
import { FormControl } from "@angular/forms";
import { UserService } from "@app/shared/service/user.service";
import { BanService } from "@app/shared/service/ban.service";
import { WorkerBanDialogComponent } from "@app/shared/components/worker-ban-dialog/worker-ban-dialog.component";
import { AuthService } from "@app/core/service";
import { WorkerAddCodeDialogComponent } from '../worker-add-code-dialog/worker-add-code-dialog.component';
let WorkerPFMenuComponent = class WorkerPFMenuComponent {
    constructor(dialog, activatedRoute, matchService, playingFieldService, dataSharingService, toastrService, userService, authService, router, banService, domSanitizer) {
        this.dialog = dialog;
        this.activatedRoute = activatedRoute;
        this.matchService = matchService;
        this.playingFieldService = playingFieldService;
        this.dataSharingService = dataSharingService;
        this.toastrService = toastrService;
        this.userService = userService;
        this.authService = authService;
        this.router = router;
        this.banService = banService;
        this.domSanitizer = domSanitizer;
        this.spinner = true;
        this.showAll = new FormControl(false);
        this.bans = [];
        this.displayedColumns = [
            "id",
            "date",
            "fromTime",
            "toTime",
            "reservation",
            "reservedBy",
            "options"
        ];
        this.displayedUserBansColumns = ["username", "options"];
    }
    ngOnInit() {
        this.activatedRoute.params.subscribe(param => {
            this.pfId = param["id"];
        });
        this.getAvailabilities();
        this.getSetup();
        this.spinner = false;
        this.showAll.valueChanges.subscribe(val => {
            this.getAvailabilities();
        });
        this.getLoggedUser();
        this.getBannedUsers();
    }
    getAvailabilities() {
        this.playingFieldService
            .getPFAvailabilities(this.pfId, this.showAll.value)
            .subscribe(val => {
            this.availabilities = val;
        });
    }
    getSetup() {
        this.playingFieldService.getPFSetup(this.pfId).subscribe(val => {
            if (val) {
                this.playingFieldSetup = val;
                this.image = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/png;base64," + this.playingFieldSetup.pfPhoto);
                this.dataSharingService.name.next(val.name);
            }
            else {
                this.setPFSetup();
            }
        });
    }
    openSetPFAvailabilityDialog() {
        const dialogRef = this.dialog.open(WorkerPFDatePickerComponent, {
            width: "400px",
            data: this.pfId
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.availabilities.push(val);
            }
        });
    }
    deletePFAvailability(availability) {
        return this.playingFieldService
            .deletePFAvailability(availability.availabilityId)
            .subscribe(val => {
            this.availabilities.splice(this.availabilities.indexOf(availability), 1);
        }, () => this.toastrService.error("Wystąpił błąd"), () => this.toastrService.success("Pomyślnie usunięto rozgrywki"));
    }
    setPFSetup() {
        const dialogRef = this.dialog.open(WorkerPFSetupDialogComponent, {
            width: "400px",
            data: {
                playingFieldSetupDto: this.playingFieldSetup,
                pfId: this.pfId,
                image: this.image
            },
            disableClose: this.playingFieldSetup == null
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.playingFieldSetup = val.pfSetup;
                this.image = val.image;
            }
        });
    }
    removeMatch(match, index) {
        this.matchService.removeMatch(match.id).subscribe(() => {
            this.availabilityTable.renderRows();
            this.availabilities[index].matchesDto.splice(this.availabilities[index].matchesDto.indexOf(match), 1);
            this.toastrService.success("Usunąłeś mecz");
            this.availabilityTable.renderRows();
        });
    }
    enlarge() {
        this.dialog.open(EnlargeImageDialogComponent, {
            width: "80%",
            data: { image: this.playingFieldSetup.pfPhoto },
            panelClass: 'custom-enlarge-dialog-container'
        });
    }
    goToUserProfile(username) {
        this.userService.getUserByUsername(username).subscribe(val => {
            this.dataSharingService.changeUser(val.id.toString());
            this.router.navigate(["users/" + val.id]);
        });
    }
    getLoggedUser() {
        if (this.authService.isLogged) {
            this.userService.getLoggedUser().subscribe(val => {
                this.loggedUser = val;
            });
        }
    }
    getBannedUsers() {
        this.banService.getBans(this.pfId).subscribe(val => {
            this.bans = val;
        });
    }
    removeBan(userId) {
        this.banService.unbanUser(this.pfId, userId).subscribe(() => {
            this.bans.splice(this.bans.indexOf(this.bans.find(val => val.userId === userId)), 1);
            this.bansTable.renderRows();
        });
    }
    banUser(userId) {
        const dialogRef = this.dialog.open(WorkerBanDialogComponent, {
            width: "30%",
            data: { pfId: this.pfId, userId: userId },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.availabilities.forEach(availability => {
                    availability.matchesDto.forEach(match => {
                        if (match.ownerId === userId) {
                            match.isBooked = false;
                            match.isPrivate = false;
                            match.ownerId = null;
                            match.ownerUsername = "";
                        }
                    });
                });
                this.availabilityTable.renderRows();
                this.bans.push(val.banDto);
                this.bansTable.renderRows();
            }
        });
    }
    acceptCode(match) {
        this.matchService.fillCode(match.id).subscribe(val => {
            match.isCodeFilled = true;
            this.toastrService.success("Dodałes kod użytkownika");
        }, () => {
            this.toastrService.error("Nie udało się dodac kodu użytkownika");
        });
    }
    openAddCodeDialog() {
        this.dialog.open(WorkerAddCodeDialogComponent, {
            width: '300px'
        });
    }
};
tslib_1.__decorate([
    ViewChild("availabilityTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], WorkerPFMenuComponent.prototype, "availabilityTable", void 0);
tslib_1.__decorate([
    ViewChild("bansTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], WorkerPFMenuComponent.prototype, "bansTable", void 0);
WorkerPFMenuComponent = tslib_1.__decorate([
    Component({
        selector: "app-worker-pfmenu",
        templateUrl: "./worker-pfmenu.component.html",
        styleUrls: ["./worker-pfmenu.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [MatDialog,
        ActivatedRoute,
        MatchService,
        PlayingFieldService,
        DataSharingService,
        ToastrService,
        UserService,
        AuthService,
        Router,
        BanService,
        DomSanitizer])
], WorkerPFMenuComponent);
export { WorkerPFMenuComponent };
//# sourceMappingURL=worker-pfmenu.component.js.map