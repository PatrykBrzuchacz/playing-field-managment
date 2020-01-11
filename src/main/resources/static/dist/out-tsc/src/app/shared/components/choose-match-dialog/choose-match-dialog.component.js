import * as tslib_1 from "tslib";
import { Component, Inject, ViewChild } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatTable, MatPaginator, MatSort } from "@angular/material";
import { MatchService } from "@app/shared/service/match.service";
import { UserService } from "@app/shared/service/user.service";
import { Router } from "@angular/router";
import { InviteService } from "@app/shared/service/invite.service";
import { ToastrService } from "ngx-toastr";
import { merge, of } from "rxjs";
import { startWith, switchMap, map, catchError } from "rxjs/operators";
let ChooseMatchDialogComponent = class ChooseMatchDialogComponent {
    constructor(data, dialogRef, toastrService, matchService, userService, router, inviteService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.toastrService = toastrService;
        this.matchService = matchService;
        this.userService = userService;
        this.router = router;
        this.inviteService = inviteService;
        this.matchesDto = [];
        this.arleadyRequestedMatchIds = [];
        this.displayedMatchColumns = [
            "date",
            "fromTime",
            "toTime",
            "reservation",
            "private",
            "size"
        ];
    }
    ngOnInit() {
        this.getMatches();
    }
    getMatches() {
        this.matchPaginator.pageSize = 5;
        merge(this.sort.sortChange, this.matchPaginator.page, this.matchPaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: [`matchFromDate,asc`, `matchFromTime,asc`],
                page: this.matchPaginator.pageIndex + "",
                size: this.matchPaginator.pageSize + ""
            };
            return this.matchService.getMatchesByUserIdAndReceiverNot(this.data.loggedUser.id, params);
        }), map((data) => {
            this.matchLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => this.getUserRequestsId(data));
    }
    sendRequest() {
        this.inviteService
            .sendInvite(this.data.user.id, this.selectedRowId)
            .subscribe(val => {
            this.dialogRef.close();
            this.toastrService.success("Wysłano zaproszenie do meczu");
        }, () => this.toastrService.error("Nie udało się wysłać zaproszenia do meczu"));
    }
    goToUserProfile(username) {
        this.userService.getUserByUsername(username).subscribe(val => {
            this.dialogRef.close();
            this.router.navigate(["users/" + val.id]);
        });
    }
    selectMatch(row) {
        this.selectedRowId = row.id;
    }
    getUserRequestsId(matchesDto) {
        this.inviteService.getUserRequests(this.data.user.id).subscribe(val => {
            this.arleadyRequestedMatchIds = val;
            this.arleadyRequestedMatchIds.forEach(id => {
                const match = matchesDto.find(val => id === val.id);
                if (match) {
                    matchesDto.splice(this.matchesDto.indexOf(match), 1);
                }
            });
            this.matchesDto = matchesDto;
            this.matchWithLocationDtoTable.renderRows();
        });
    }
};
tslib_1.__decorate([
    ViewChild(MatPaginator, { static: true }),
    tslib_1.__metadata("design:type", MatPaginator)
], ChooseMatchDialogComponent.prototype, "matchPaginator", void 0);
tslib_1.__decorate([
    ViewChild(MatSort, { static: true }),
    tslib_1.__metadata("design:type", MatSort)
], ChooseMatchDialogComponent.prototype, "sort", void 0);
tslib_1.__decorate([
    ViewChild("availabilityTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], ChooseMatchDialogComponent.prototype, "matchWithLocationDtoTable", void 0);
ChooseMatchDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-choose-match-dialog",
        templateUrl: "./choose-match-dialog.component.html",
        styleUrls: ["./choose-match-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        ToastrService,
        MatchService,
        UserService,
        Router,
        InviteService])
], ChooseMatchDialogComponent);
export { ChooseMatchDialogComponent };
//# sourceMappingURL=choose-match-dialog.component.js.map