import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { UserService } from "@app/shared/service/user.service";
import { GeoLocationService } from "@app/shared/service/geo-location.service";
import { LocationService } from "@app/shared/service/location.service";
import { MatDialog } from "@angular/material/dialog";
import { AuthService } from "@app/core/service";
import { of } from "rxjs";
import { switchMap, map, catchError, tap } from "rxjs/operators";
import { Position } from "@app/shared/model";
import { DomSanitizer } from "@angular/platform-browser";
import { FriendsService } from "@app/shared/service/friends.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { MessageDialogComponent } from "@app/shared/components/message-dialog/message-dialog.component";
import { MessageService } from "@app/shared/service/message.service";
import { ChooseMatchDialogComponent } from "@app/shared/components/choose-match-dialog/choose-match-dialog.component";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
let SearchUserComponent = class SearchUserComponent {
    constructor(userService, router, geoLocationService, locationService, dialog, authService, friendsService, toastrService, messageService, domSanitizer, dataSharingService) {
        this.userService = userService;
        this.router = router;
        this.geoLocationService = geoLocationService;
        this.locationService = locationService;
        this.dialog = dialog;
        this.authService = authService;
        this.friendsService = friendsService;
        this.toastrService = toastrService;
        this.messageService = messageService;
        this.domSanitizer = domSanitizer;
        this.dataSharingService = dataSharingService;
        this.users = [];
        this.handleSelectedLocationChange = () => {
            return this.searchForm.controls.city.valueChanges.pipe(switchMap((address) => {
                return this.geoLocationService.getPositions(address);
            }), map((data) => {
                return data && data;
            }), catchError((error) => {
                return of([]);
            }), tap((data) => {
                console.log(data);
                this.searchedLocations = data;
            }));
        };
    }
    ngOnInit() {
        this.initForm();
        this.locations = this.handleSelectedLocationChange();
        this.getLoggedUser();
        if (!this.authService.isLogged()) {
            this.searchUsers();
        }
    }
    initForm() {
        this.searchForm = new FormGroup({
            city: new FormControl(""),
            position: new FormControl("")
        });
    }
    searchUsers() {
        let position;
        if (this.searchForm.value.position === "Bramkarz") {
            position = Position.Bramkarz;
        }
        else if (this.searchForm.value.position === "Obrońca") {
            position = Position.Obrońca;
        }
        else if (this.searchForm.value.position === "Pomocnik") {
            position = Position.Pomocnik;
        }
        else if (this.searchForm.value.position === "Napastnik") {
            position = Position.Napastnik;
        }
        else
            position = "";
        this.userService
            .getUsers(this.searchForm.value.city, position)
            .subscribe(users => {
            this.users = [];
            users.forEach(val => {
                if (val.userDto.position === "GOALKEEPER") {
                    val.userDto.position = "Bramkarz";
                }
                else if (val.userDto.position === "DEFENDER") {
                    val.userDto.position = "Obrońca";
                }
                else if (val.userDto.position === "MIDFIELDER") {
                    val.userDto.position = "Pomocnik";
                }
                else if (val.userDto.position === "FORWARD") {
                    val.userDto.position = "Napastnik";
                }
                else {
                    val.userDto.position = "";
                }
                if (val.userDto.avatar) {
                    val.userDto.avatar = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/png;base64," + val.userDto.avatar);
                }
                if (this.loggedUser) {
                    if (val.userDto.id !== this.loggedUser.id)
                        this.users.push(val);
                }
                else
                    this.users.push(val);
            });
        });
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(val => {
                this.loggedUser = val;
                this.searchUsers();
            });
        }
    }
    sendMessage(user) {
        this.messageService.getMessages(user.id).subscribe(val => {
            this.dialog.open(MessageDialogComponent, {
                width: "620px",
                data: {
                    messages: val,
                    id: user.id,
                    loggedUser: this.loggedUser,
                    username: user.username
                },
                panelClass: "custom-dialog-container"
            });
        });
    }
    addToFriends(user) {
        if (user.isRequestSended) {
            this.toastrService.error("Wysłałeś już zaproszenie do znajomych do tego użytkownika");
        }
        else {
            this.friendsService.sendRequest(user.userDto.id).subscribe(() => {
                user.isRequestSended = true;
                this.toastrService.success("Wysłano zaproszenie do znajomych");
            });
        }
    }
    goToUserProfile(user) {
        this.dataSharingService.changeUser(user.id);
        this.router.navigate(["users/" + user.id]);
    }
    sendRequest(user) {
        this.dialog.open(ChooseMatchDialogComponent, {
            width: "800px",
            data: { user: user.userDto, loggedUser: this.loggedUser }
        });
    }
};
SearchUserComponent = tslib_1.__decorate([
    Component({
        selector: "app-search-user",
        templateUrl: "./search-user.component.html",
        styleUrls: ["./search-user.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [UserService,
        Router,
        GeoLocationService,
        LocationService,
        MatDialog,
        AuthService,
        FriendsService,
        ToastrService,
        MessageService,
        DomSanitizer,
        DataSharingService])
], SearchUserComponent);
export { SearchUserComponent };
//# sourceMappingURL=search-user.component.js.map