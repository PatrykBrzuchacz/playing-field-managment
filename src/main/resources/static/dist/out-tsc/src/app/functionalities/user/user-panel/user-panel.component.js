import * as tslib_1 from "tslib";
import { Component, ViewChild } from "@angular/core";
import { UserService } from "@app/shared/service/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { MatDialog } from "@angular/material/dialog";
import { MatTable } from "@angular/material/table";
import { EnlargeImageDialogComponent } from "@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component";
import { AuthService } from "@app/core/service";
import { EditUserDataComponent } from "../edit-user-data/edit-user-data.component";
import { FriendsService } from "@app/shared/service/friends.service";
import { ToastrService } from "ngx-toastr";
import { Subscription, merge, of } from "rxjs";
import { MatchService } from "@app/shared/service/match.service";
import { TeamsDialogComponent } from "@app/functionalities/playing-field/components/teams-dialog/teams-dialog.component";
import { TeamService } from "@app/shared/service/team.service";
import { MessageDialogComponent } from "@app/shared/components/message-dialog/message-dialog.component";
import { MessageService } from "@app/shared/service/message.service";
import { ChooseMatchDialogComponent } from "@app/shared/components/choose-match-dialog/choose-match-dialog.component";
import { WorkerBanDialogComponent } from "@app/shared/components/worker-ban-dialog/worker-ban-dialog.component";
import { BanService } from "@app/shared/service/ban.service";
import { InviteService } from "@app/shared/service/invite.service";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { MatPaginator, MatSort } from "@angular/material";
import { startWith, switchMap, map, catchError } from "rxjs/operators";
let UserPanelComponent = class UserPanelComponent {
    constructor(activatedRoute, userService, domSanitizer, dialog, router, friendsService, toastrService, matchService, teamService, messageService, authService, inviteService, banService, dataSharingService) {
        this.activatedRoute = activatedRoute;
        this.userService = userService;
        this.domSanitizer = domSanitizer;
        this.dialog = dialog;
        this.router = router;
        this.friendsService = friendsService;
        this.toastrService = toastrService;
        this.matchService = matchService;
        this.teamService = teamService;
        this.messageService = messageService;
        this.authService = authService;
        this.inviteService = inviteService;
        this.banService = banService;
        this.dataSharingService = dataSharingService;
        this.loading = true;
        this.sendedFriendRequests = [];
        this.friendRequestDto = [];
        this.friends = [];
        this.matchesDto = [];
        this.displayedColumns = ["username", "options"];
        this.invitesDto = [];
        this.displayedFriendsColumns = [
            "username",
            "position",
            "lastLogin",
            "options"
        ];
        this.displayedMatchColumns = [
            "address",
            "date",
            "fromTime",
            "reservation",
            "private",
            "size",
            "rate",
            "options"
        ];
        this.displayedInvitesColumns = [
            "address",
            "date",
            "time",
            "reservation",
            "options"
        ];
        this.displayedConversationsColumns = ["username", "options"];
        this.subscription = new Subscription();
        this.actualFriend = false;
        this.isBanned = false;
        this.conversationsDto = [];
    }
    ngOnInit() {
        this.activatedRoute.params.subscribe(param => {
            this.paramId = param["id"];
        });
        if (this.paramId) {
            this.dataSharingService.currentUser.subscribe(val => {
                if (val === "default") {
                    this.getUser(this.paramId);
                    this.getFriends(this.paramId);
                }
                else {
                    this.getUser(val);
                    this.getFriends(val);
                }
            });
        }
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
    getUser(id) {
        this.subscription.add(this.userService.getUser(id).subscribe(val => {
            this.user = val;
            if (val.avatar) {
                this.avatar = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/png;base64," + this.user.avatar);
            }
            if (val.position === "GOALKEEPER") {
                this.user.position = "Bramkarz";
            }
            else if (val.position === "DEFENDER") {
                this.user.position = "Obrońca";
            }
            else if (val.position === "MIDFIELDER") {
                this.user.position = "Pomocnik";
            }
            else if (val.position === "FORWARD") {
                this.user.position = "Napastnik";
            }
            else
                this.user.position = "";
            this.getLoggedUser();
            this.handleMatchesTableChange();
            this.loading = false;
        }, () => this.router.navigate(["search"])));
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.subscription.add(this.userService.getLoggedUserWithAvatar().subscribe(val => {
                this.loggedUser = val;
                this.friendsService.getSendedRequests().subscribe(friendsRequests => {
                    this.sendedFriendRequests = friendsRequests;
                    if (this.sendedFriendRequests.indexOf(this.user.username) !== -1) {
                        this.sended = true;
                    }
                });
                if (this.loggedUser.playingFieldId &&
                    this.loggedUser.id !== this.user.id) {
                    this.getUserBans();
                }
                if (this.loggedUser.id === this.user.id) {
                    this.handleMatchInvitesTableChange();
                    this.handleConversationTableChange();
                    this.handleFriendInvitesTableChange();
                }
            }));
        }
    }
    editUser() {
        const dialogRef = this.dialog.open(EditUserDataComponent, {
            width: "60%",
            data: { user: this.user, avatar: this.avatar }
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.user.city = val.user.city;
                this.user.age = val.user.age;
                this.user.email = val.user.email;
                this.user.phoneNumber = val.user.phoneNumber;
                this.user.firstName = val.user.firstName;
                this.user.lastName = val.user.lastName;
                if (val.user.position === "GOALKEEPER") {
                    this.user.position = "Bramkarz";
                }
                else if (val.user.position === "DEFENDER") {
                    this.user.position = "Obrońca";
                }
                else if (val.user.position === "MIDFIELDER") {
                    this.user.position = "Pomocnik";
                }
                else
                    this.user.position = "Napastnik";
                if (val.avatar) {
                    this.avatar = val.avatar;
                }
            }
        });
    }
    sendMessage(id, username) {
        this.messageService.getMessages(id).subscribe(val => {
            this.dialog.open(MessageDialogComponent, {
                width: "620px",
                data: {
                    messages: val,
                    id: id,
                    loggedUser: this.loggedUser,
                    username: username
                },
                panelClass: "custom-dialog-container"
            });
        });
    }
    addToFriends() {
        if (this.sended) {
            this.toastrService.error("Wysłałeś już zaproszenie do znajomych do tego użytkownika");
        }
        else if (this.actualFriend) {
            this.toastrService.error("Użytkownik jest już twoim znajomym");
        }
        else {
            this.friendsService.sendRequest(this.user.id).subscribe(() => {
                this.sended = true;
                this.toastrService.success("Wysłano zaproszenie do znajomych");
            });
        }
    }
    removeRequest(friendRequest) {
        this.friendsService.removeRequest(friendRequest.id).subscribe(val => {
            this.friendRequestDto.splice(this.friendRequestDto.indexOf(friendRequest), 1);
            this.friendRequestTable.renderRows();
        });
    }
    acceptRequest(friendRequest) {
        this.friendsService.acceptRequest(friendRequest.id).subscribe(val => {
            this.friendRequestDto.splice(this.friendRequestDto.indexOf(friendRequest), 1);
            if (val.position === "GOALKEEPER") {
                val.position = "Bramkarz";
            }
            else if (val.position === "DEFENDER") {
                val.position = "Obrońca";
            }
            else if (val.position === "MIDFIELDER") {
                val.position = "Pomocnik";
            }
            else
                val.position = "Napastnik";
            this.friends.push(val);
            this.friendRequestTable.renderRows();
        });
    }
    handleFriendsInviteTableChange() {
        this.friendInvitePaginator.pageSize = 5;
        merge(this.sort.sortChange, this.friendInvitePaginator.page, this.friendInvitePaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: ``,
                page: this.friendInvitePaginator.pageIndex + "",
                size: this.friendInvitePaginator.pageSize + ""
            };
            return this.friendsService.getSendedRequests(params);
        }), map((data) => {
            this.friendsInviteLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => {
            this.sendedFriendRequests = data;
            if (this.sendedFriendRequests.indexOf(this.user.username) !== -1) {
                this.sended = true;
            }
        });
    }
    deleteFriend(friend) {
        this.friendsService.deleteFriend(friend.id).subscribe(val => {
            this.friends.splice(this.friends.indexOf(friend), 1);
        });
    }
    getFriends(id) {
        this.subscription.add(this.friendsService.getAllFriends(id).subscribe(val => {
            this.friends = val;
            val.forEach(friend => {
                if (friend.avatar) {
                    friend.avatar = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/png;base64," + friend.avatar);
                }
                if (friend.position === "GOALKEEPER") {
                    friend.position = "Bramkarz";
                }
                else if (friend.position === "DEFENDER") {
                    friend.position = "Obrońca";
                }
                else if (friend.position === "MIDFIELDER") {
                    friend.position = "Pomocnik";
                }
                else
                    friend.position = "Napastnik";
                if (this.loggedUser) {
                    if (friend.id === this.user.id) {
                        //?
                        this.actualFriend = true;
                    }
                }
            });
        }));
    }
    enlarge() {
        this.dialog.open(EnlargeImageDialogComponent, {
            width: "80%",
            data: { image: this.avatar },
            panelClass: "custom-enlarge-dialog-container"
        });
    }
    checkMatch(match) {
        const dialogRef = this.dialog.open(TeamsDialogComponent, {
            width: "800px",
            data: {
                isPrivate: match.isPrivate,
                matchId: match.id,
                ownerId: match.ownerId,
                pfId: match.pfId,
                editable: match.editable
            },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            match.size = val;
        });
    }
    goToUserProfile(username) {
        this.userService.getUserByUsername(username).subscribe(val => {
            this.router.navigate(["users/" + val.id]);
            this.getUser(val.id);
            this.getFriends(val.id);
            this.handleMatchInvitesTableChange();
        });
    }
    sendRequest() {
        this.dialog.open(ChooseMatchDialogComponent, {
            width: "800px",
            data: { user: this.user, loggedUser: this.loggedUser }
        });
    }
    unbook(match) {
        this.matchService.unbookPF(match.id).subscribe(val => {
            this.matchesDto.splice(this.matchesDto.indexOf(match), 1);
            this.availabilityTable.renderRows();
            this.toastrService.success("Pomyślnie usunąłeś rezerwację meczu");
        }, () => {
            this.toastrService.error("Nie udało się usunąć meczu");
        });
    }
    banUser(userId) {
        const dialogRef = this.dialog.open(WorkerBanDialogComponent, {
            width: "30%",
            data: { pfId: this.loggedUser.playingFieldId, userId: userId },
            autoFocus: false
        });
        dialogRef.afterClosed().subscribe(val => {
            if (val) {
                this.isBanned = true;
                this.toastrService.success("Zablokowałeś użytkownika na twoim orliku");
            }
        });
    }
    unbanUser(userId) {
        this.banService
            .unbanUser(this.loggedUser.playingFieldId, userId)
            .subscribe(() => {
            this.isBanned = false;
            this.toastrService.success("Odblokowałeś użytkownika na twoim orliku");
        });
    }
    getUserBans() {
        this.banService
            .isUserBanned(this.user.id, this.loggedUser.playingFieldId)
            .subscribe(val => {
            this.isBanned = val;
        });
    }
    acceptMatchRequest(invite) {
        if (invite.matchWithLocationDto.size < invite.matchWithLocationDto.maxSize) {
            this.inviteService.acceptInvite(invite.id).subscribe(val => {
                this.invitesDto.splice(this.invitesDto.indexOf(invite), 1);
                this.matchesDto.push(val);
                this.availabilityTable.renderRows();
                this.invitesTable.renderRows();
                this.toastrService.success("Zaakceptowałeś zaproszenie do meczu");
            });
        }
        else {
            this.toastrService.error("Nie możesz dołączyć do meczu, jest pełny");
        }
    }
    removeMatchRequest(invite) {
        this.inviteService.declineInvite(invite.id).subscribe(() => {
            this.invitesDto.splice(this.invitesDto.indexOf(invite), 1);
            this.invitesTable.renderRows();
        });
    }
    handleMatchesTableChange() {
        this.matchPaginator.pageSize = 5;
        merge(this.sort.sortChange, this.matchPaginator.page, this.matchPaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: [`matchFromDate,asc`, `matchFromTime,asc`],
                page: this.matchPaginator.pageIndex + "",
                size: this.matchPaginator.pageSize + ""
            };
            return this.matchService.getMatchesByUserId(this.user.id, params);
        }), map((data) => {
            this.matchLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => {
            console.log(data);
            this.matchesDto = data;
        });
    }
    handleConversationTableChange() {
        this.conversationPaginator.pageSize = 5;
        merge(this.sort.sortChange, this.conversationPaginator.page, this.conversationPaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: `lastMessage,desc`,
                page: this.conversationPaginator.pageIndex + "",
                size: this.conversationPaginator.pageSize + ""
            };
            return this.messageService.getConservations(params);
        }), map((data) => {
            this.conversationLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => (this.conversationsDto = data));
    }
    handleMatchInvitesTableChange() {
        this.invitePaginator.pageSize = 5;
        merge(this.sort.sortChange, this.invitePaginator.page, this.invitePaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: ``,
                page: this.invitePaginator.pageIndex + "",
                size: this.invitePaginator.pageSize + ""
            };
            return this.inviteService.getInvites(params);
        }), map((data) => {
            this.inviteLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => {
            console.log(data);
            this.invitesDto = data;
            this.invitesTable.renderRows();
        });
    }
    handleFriendInvitesTableChange() {
        this.friendInvitePaginator.pageSize = 5;
        merge(this.sort.sortChange, this.friendInvitePaginator.page, this.friendInvitePaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: ``,
                page: this.friendInvitePaginator.pageIndex + "",
                size: this.friendInvitePaginator.pageSize + ""
            };
            return this.friendsService.getFriendRequests(params);
        }), map((data) => {
            this.friendsInviteLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => (this.friendRequestDto = data));
    }
};
tslib_1.__decorate([
    ViewChild("friendRequestTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserPanelComponent.prototype, "friendRequestTable", void 0);
tslib_1.__decorate([
    ViewChild("friendsTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserPanelComponent.prototype, "friendsTable", void 0);
tslib_1.__decorate([
    ViewChild("availabilityTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserPanelComponent.prototype, "availabilityTable", void 0);
tslib_1.__decorate([
    ViewChild("invitesTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserPanelComponent.prototype, "invitesTable", void 0);
tslib_1.__decorate([
    ViewChild("matchPaginator", { static: false }),
    tslib_1.__metadata("design:type", MatPaginator)
], UserPanelComponent.prototype, "matchPaginator", void 0);
tslib_1.__decorate([
    ViewChild("conversationPaginator", { static: false }),
    tslib_1.__metadata("design:type", MatPaginator)
], UserPanelComponent.prototype, "conversationPaginator", void 0);
tslib_1.__decorate([
    ViewChild("invitePaginator", { static: false }),
    tslib_1.__metadata("design:type", MatPaginator)
], UserPanelComponent.prototype, "invitePaginator", void 0);
tslib_1.__decorate([
    ViewChild("friendInvitePaginator", { static: false }),
    tslib_1.__metadata("design:type", MatPaginator)
], UserPanelComponent.prototype, "friendInvitePaginator", void 0);
tslib_1.__decorate([
    ViewChild(MatSort, { static: false }),
    tslib_1.__metadata("design:type", MatSort)
], UserPanelComponent.prototype, "sort", void 0);
UserPanelComponent = tslib_1.__decorate([
    Component({
        selector: "app-user-panel",
        templateUrl: "./user-panel.component.html",
        styleUrls: ["./user-panel.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [ActivatedRoute,
        UserService,
        DomSanitizer,
        MatDialog,
        Router,
        FriendsService,
        ToastrService,
        MatchService,
        TeamService,
        MessageService,
        AuthService,
        InviteService,
        BanService,
        DataSharingService])
], UserPanelComponent);
export { UserPanelComponent };
//# sourceMappingURL=user-panel.component.js.map