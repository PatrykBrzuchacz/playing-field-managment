import * as tslib_1 from "tslib";
import { Component, Inject, ViewChild } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from "@angular/material/dialog";
import { MatTable } from "@angular/material/table";
import { TeamService } from "@app/shared/service/team.service";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { Router } from "@angular/router";
import { InviteService } from "@app/shared/service/invite.service";
import { FormControl } from "@angular/forms";
import { of } from "rxjs";
import { startWith, map } from "rxjs/operators";
import { WorkerBanDialogComponent } from "@app/shared/components/worker-ban-dialog/worker-ban-dialog.component";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
let TeamsDialogComponent = class TeamsDialogComponent {
    constructor(data, dialog, dialogRef, teamService, toastrService, authService, userService, inviteService, router, dataSharingService) {
        this.data = data;
        this.dialog = dialog;
        this.dialogRef = dialogRef;
        this.teamService = teamService;
        this.toastrService = toastrService;
        this.authService = authService;
        this.userService = userService;
        this.inviteService = inviteService;
        this.router = router;
        this.dataSharingService = dataSharingService;
        this.usernamesWithId = [];
        this.usersInvited = [];
        this.isPrivate = false;
        this.displayedColumns = ["id", "username", "position", "options"];
        this.displayedInvitesColumns = ["username", "position", "options"];
        this.usernameForm = new FormControl("");
        this.onlyFriends = new FormControl(true);
        this.friendsClone = [];
    }
    ngOnInit() {
        this.teamService.getTeamsByMatch(this.data.matchId).subscribe(val => {
            for (let team of val) {
                for (let player of team.players) {
                    if (player.position === "GOALKEEPER") {
                        player.position = "Bramkarz";
                    }
                    else if (player.position === "DEFENDER") {
                        player.position = "Obrońca";
                    }
                    else if (player.position === "MIDFIELDER") {
                        player.position = "Pomocnik";
                    }
                    else if (player.position === "FORWARD") {
                        player.position = "Napastnik";
                    }
                    else {
                        player.position = "";
                    }
                }
            }
            this.teamsDto = val;
        });
        this.isPrivate = this.data.isPrivate;
        this.matchId = this.data.matchId;
        this.getLoggedUser();
        this.getMatchInvites();
        this.dialogRef
            .beforeClose()
            .subscribe(() => this.dialogRef.close(this.teamsDto[0].size + this.teamsDto[1].size));
    }
    joinTeam(team) {
        if (this.teamsDto[0].players.find(it => it.username === this.loggedUser.username) ||
            this.teamsDto[1].players.find(it => it.username === this.loggedUser.username)) {
            this.teamService.swapTeam(this.matchId).subscribe(() => {
                this.teamsDto[this.teamsDto.indexOf(team)].players.push({
                    id: this.loggedUser.id,
                    username: this.loggedUser.username,
                    position: this.loggedUser.position
                });
                if (this.teamsDto.indexOf(team) === 0) {
                    this.teamsDto[1].players.splice(this.teamsDto[1].players.indexOf(this.teamsDto[1].players.find(val => this.loggedUser.username === val.username)), 1);
                    this.teamsDto[0].size += 1;
                    this.teamsDto[1].size -= 1;
                }
                else if (this.teamsDto.indexOf(team) === 1) {
                    this.teamsDto[0].players.splice(this.teamsDto[0].players.indexOf(this.teamsDto[0].players.find(val => this.loggedUser.username === val.username)), 1);
                    this.teamsDto[1].size += 1;
                    this.teamsDto[0].size -= 1;
                }
                this.teamTable1.renderRows();
                this.teamTable2.renderRows();
                this.toastrService.success("Zmieniłeś drużynę");
            }, () => {
                this.toastrService.error("Nie udało się zamienić drużyn");
            });
        }
        else {
            this.teamService.joinToTeam(team.id).subscribe(() => {
                this.teamsDto[this.teamsDto.indexOf(team)].players.push({
                    id: this.loggedUser.id,
                    username: this.loggedUser.username,
                    position: this.loggedUser.position
                });
                this.teamsDto[this.teamsDto.indexOf(team)].size += 1;
                this.toastrService.success("Doszedłeś do " + team.teamName);
            }, () => {
                this.toastrService.error("Nie udało się dołączyć do drużyny, przepraszamy");
            }, () => {
                this.teamTable1.renderRows();
                this.teamTable2.renderRows();
            });
        }
    }
    existInTeam(team) {
        return team.players.find(player => this.loggedUser.username === player.username);
    }
    exitTeam(team) {
        this.teamService.exitTeam(team.id).subscribe(() => {
            const searchedTeam = this.teamsDto[this.teamsDto.indexOf(team)];
            searchedTeam.players.splice(searchedTeam.players.indexOf(searchedTeam.players.find(val => this.loggedUser.username === val.username)), 1);
            searchedTeam.size -= 1;
            this.toastrService.success("Pomyślnie opuściłeś drużynę");
        }, () => {
            this.toastrService.error("Nie udało się wyjść z drużyny");
        }, () => {
            this.teamTable1.renderRows();
            this.teamTable2.renderRows();
        });
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(response => {
                this.loggedUser = response;
                if (this.loggedUser.position === "GOALKEEPER") {
                    this.loggedUser.position = "Bramkarz";
                }
                else if (this.loggedUser.position === "DEFENDER") {
                    this.loggedUser.position = "Obrońca";
                }
                else if (this.loggedUser.position === "MIDFIELDER") {
                    this.loggedUser.position = "Pomocnik";
                }
                else if (this.loggedUser.position === "FORWARD") {
                    this.loggedUser.position = "Napastnik";
                }
                else {
                    this.loggedUser.position = "";
                }
                this.getAllUsersUsername();
                this.onlyFriends.valueChanges.subscribe(val => {
                    this.getAllUsersUsername();
                });
            });
        }
    }
    getMatchInvites() {
        this.inviteService.getMatchInvites(this.matchId).subscribe(val => {
            val.forEach(val => {
                if (val.user.position === "GOALKEEPER") {
                    val.user.position = "Bramkarz";
                }
                else if (val.user.position === "DEFENDER") {
                    val.user.position = "Obrońca";
                }
                else if (val.user.position === "MIDFIELDER") {
                    val.user.position = "Pomocnik";
                }
                else if (val.user.position === "FORWARD") {
                    val.user.position = "Napastnik";
                }
                else {
                    val.user.position = "";
                }
            });
            this.usersInvited = val;
            if (this.friendsClone) {
                this.friendsClone = JSON.parse(JSON.stringify(this.usernamesWithId));
            }
        });
    }
    goToUserProfile(username) {
        this.userService.getUserByUsername(username).subscribe(val => {
            this.dialogRef.close();
            this.dataSharingService.changeUser(localStorage.getItem(val.id.toString()));
            this.router.navigate(["users/" + val.id]);
        });
    }
    sendInvite(id) {
        this.inviteService.sendInvite(id, this.matchId).subscribe(val => {
            const addedUser = this.usernamesWithId.find(val => val.id === id);
            this.usernamesWithId.splice(this.usernamesWithId.indexOf(addedUser), 1);
            this.usersInvited.push({ id: val, user: addedUser });
            this.invitesTable.renderRows();
            this.usernameForm.setValue("");
        });
    }
    deleteRequest(inviteWithUserDto) {
        this.inviteService.declineInvite(inviteWithUserDto.id).subscribe(() => {
            this.usersInvited.splice(this.usersInvited.indexOf(inviteWithUserDto), 1);
            if (this.onlyFriends.value) {
                if (this.friendsClone.find(val => val === inviteWithUserDto)) {
                    this.usernamesWithId.push(inviteWithUserDto.user);
                }
            }
            else {
                this.usernamesWithId.push(inviteWithUserDto.user);
            }
            this.filteredUsers = of(this.usernamesWithId);
            this.invitesTable.renderRows();
        });
    }
    getAllUsersUsername() {
        this.userService
            .getUsersUsername(this.matchId, this.onlyFriends.value)
            .subscribe(val => {
            this.usernamesWithId = val;
            for (let player of this.usernamesWithId) {
                if (player.position === "GOALKEEPER") {
                    player.position = "Bramkarz";
                }
                else if (player.position === "DEFENDER") {
                    player.position = "Obrońca";
                }
                else if (player.position === "MIDFIELDER") {
                    player.position = "Pomocnik";
                }
                else if (player.position === "FORWARD") {
                    player.position = "Napastnik";
                }
                else
                    player.position = "";
            }
            this.filteredUsers = this.usernameForm.valueChanges.pipe(startWith(""), map(user => user ? this._filterUsers(user) : this.usernamesWithId.slice()));
        });
    }
    banUser(userId, teamIndex) {
        const dialogRef = this.dialog.open(WorkerBanDialogComponent, {
            width: "30%",
            data: { pfId: this.data.pfId, userId: userId },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                if (userId == this.data.ownerId) {
                    window.location.reload();
                }
                else {
                    this.teamsDto[teamIndex].players.splice(this.teamsDto[teamIndex].players.indexOf(this.teamsDto[teamIndex].players.find(player => player.id === userId)), 1);
                }
            }
            this.teamTable1.renderRows();
            this.teamTable2.renderRows();
        });
    }
    isTeamChangePossible() {
        if (!this.isPrivate) {
            this.changeTeam = true;
        }
        else if (this.existInTeam(this.teamsDto[0]) ||
            this.existInTeam(this.teamsDto[1])) {
            this.changeTeam = true;
        }
        else {
            this.changeTeam = false;
        }
    }
    _filterUsers(username) {
        const filterValue = username.toLowerCase();
        return this.usernamesWithId.filter(user => user.username.toLowerCase().indexOf(filterValue) === 0);
    }
};
tslib_1.__decorate([
    ViewChild("teamTable1", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], TeamsDialogComponent.prototype, "teamTable1", void 0);
tslib_1.__decorate([
    ViewChild("teamTable2", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], TeamsDialogComponent.prototype, "teamTable2", void 0);
tslib_1.__decorate([
    ViewChild("invitesTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], TeamsDialogComponent.prototype, "invitesTable", void 0);
TeamsDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-teams-dialog",
        templateUrl: "./teams-dialog.component.html",
        styleUrls: ["./teams-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialog,
        MatDialogRef,
        TeamService,
        ToastrService,
        AuthService,
        UserService,
        InviteService,
        Router,
        DataSharingService])
], TeamsDialogComponent);
export { TeamsDialogComponent };
//# sourceMappingURL=teams-dialog.component.js.map