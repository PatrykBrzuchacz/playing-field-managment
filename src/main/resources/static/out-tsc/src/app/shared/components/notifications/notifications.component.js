import * as tslib_1 from "tslib";
import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { NotificationService } from "@app/shared/service/notification.service";
import { MessageService } from "@app/shared/service/message.service";
import { MatDialog } from "@angular/material";
import { MessageDialogComponent } from "../message-dialog/message-dialog.component";
import { FriendsService } from "@app/shared/service/friends.service";
import { InviteService } from "@app/shared/service/invite.service";
import { MatchService } from "@app/shared/service/match.service";
import { ToastrService } from "ngx-toastr";
import { TeamsDialogComponent } from "@app/functionalities/playing-field/components/teams-dialog/teams-dialog.component";
import { TeamService } from "@app/shared/service/team.service";
let NotificationsComponent = class NotificationsComponent {
    constructor(router, dialog, dataSharingService, authService, userService, messageService, notificationService, friendsService, inviteService, matchService, toastrService, teamService) {
        this.router = router;
        this.dialog = dialog;
        this.dataSharingService = dataSharingService;
        this.authService = authService;
        this.userService = userService;
        this.messageService = messageService;
        this.notificationService = notificationService;
        this.friendsService = friendsService;
        this.inviteService = inviteService;
        this.matchService = matchService;
        this.toastrService = toastrService;
        this.teamService = teamService;
        this.activeNotifications = false;
        this.notifications = [];
    }
    ngOnInit() {
        this.getNotifications();
    }
    getNotifications() {
        this.activeNotifications = !this.activeNotifications;
        this.notificationService.getNotifications().subscribe(notifications => {
            this.notifications = notifications;
            this.notifications.forEach(val => {
                if (val.displayed) {
                    let word;
                    if (val.accepted) {
                        word = "Zaakceptowałeś";
                    }
                    else {
                        word = "Odrzuciłeś";
                    }
                    val.title = val.title.replace("Otrzymałeś", word);
                }
            });
        });
    }
    setAsDisplayed(notification, accepted) {
        if (!notification.displayed) {
            this.notificationService
                .setAsDisplayed(notification.id, accepted)
                .subscribe(() => {
                notification.displayed = true;
            });
        }
    }
    setAllAsDisplayed(event) {
        event.stopPropagation();
        event.preventDefault();
        this.notificationService.setAllAsDisplayed().subscribe(() => {
            this.notifications.forEach(val => {
                val.displayed = true;
            });
        });
    }
    openMessage(notification) {
        this.setAsDisplayed(notification, false);
        this.messageService.getMessages(notification.senderId).subscribe(val => {
            this.dialog.open(MessageDialogComponent, {
                width: "620px",
                data: {
                    messages: val,
                    id: notification.senderId,
                    loggedUser: this.loggedUser,
                    username: notification.senderUsername
                },
                panelClass: "custom-dialog-container"
            });
        });
    }
    goToUserProfile(user) {
        this.dataSharingService.changeUser(user.id);
        this.router.navigate(["users/" + user.id]);
    }
    acceptFriendRequest(event, notification) {
        event.stopPropagation();
        event.preventDefault();
        this.friendsService.acceptRequest(notification.entityId).subscribe(val => {
            notification.accepted = true;
            notification.title = notification.title.replace("Otrzymałeś", "Zaakceptowałeś");
            this.setAsDisplayed(notification, true);
            //sharowany serwis by sie odswiezylo
        });
    }
    declineFriendRequest(event, notification) {
        event.stopPropagation();
        event.preventDefault();
        this.friendsService.removeRequest(notification.entityId).subscribe(val => {
            this.setAsDisplayed(notification, false);
            notification.title = notification.title.replace("Otrzymałeś", "Odrzuciłeś");
        });
    }
    acceptMatchRequest(event, notification) {
        event.stopPropagation();
        event.preventDefault();
        this.matchService.getMatchById(notification.externalId).subscribe(val => {
            if (val.size < val.maxSize) {
                this.inviteService
                    .acceptInvite(notification.entityId)
                    .subscribe(val => {
                    this.toastrService.success("Zaakceptowałeś zaproszenie do meczu");
                    this.setAsDisplayed(notification, true);
                    notification.title = notification.title.replace("Otrzymałeś", "Zaakceptowałeś");
                });
            }
            else {
                this.toastrService.error("Drużyna jest pełna!");
            }
        });
    }
    declineMatchRequest(event, notification) {
        event.stopPropagation();
        event.preventDefault();
        this.inviteService.declineInvite(notification.entityId).subscribe(() => {
            this.setAsDisplayed(notification, false);
            notification.title = notification.title.replace("Otrzymałeś", "Odrzuciłeś");
        });
    }
    openTeamDialog(notification, join) {
        this.matchService.getMatchById(notification.entityId).subscribe(match => {
            let teams;
            this.teamService.getTeamsByMatch(match.id).subscribe(val => {
                teams = val;
                if (val.length) {
                    if (join &&
                        teams &&
                        !teams[0].players.find(val => val.id === this.loggedUser.id) &&
                        !teams[1].players.find(val => val.id === this.loggedUser.id)) {
                        if (teams[0].size < teams[0].maxSize) {
                            teams[0].players.push({
                                id: this.loggedUser.id,
                                username: this.loggedUser.username,
                                position: this.loggedUser.position
                            });
                        }
                        else if (teams[1].size < teams[1].maxSize) {
                            teams[1].players.push({
                                id: this.loggedUser.id,
                                username: this.loggedUser.username,
                                position: this.loggedUser.position
                            });
                        }
                        else {
                            this.toastrService.success("Nie możesz dołączyć do meczu, jest pełen");
                        }
                    }
                    this.dialog.open(TeamsDialogComponent, {
                        width: "800px",
                        data: {
                            teamsDto: teams,
                            isPrivate: match.isPrivate,
                            matchId: match.id,
                            editable: match.editable
                        }
                    });
                }
                else {
                    this.toastrService.error("Mecz nie istnieje");
                }
            });
            this.setAsDisplayed(notification, false);
        });
    }
};
tslib_1.__decorate([
    Input(),
    tslib_1.__metadata("design:type", Object)
], NotificationsComponent.prototype, "loggedUser", void 0);
NotificationsComponent = tslib_1.__decorate([
    Component({
        selector: "app-notifications",
        templateUrl: "./notifications.component.html",
        styleUrls: ["./notifications.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [Router,
        MatDialog,
        DataSharingService,
        AuthService,
        UserService,
        MessageService,
        NotificationService,
        FriendsService,
        InviteService,
        MatchService,
        ToastrService,
        TeamService])
], NotificationsComponent);
export { NotificationsComponent };
//# sourceMappingURL=notifications.component.js.map