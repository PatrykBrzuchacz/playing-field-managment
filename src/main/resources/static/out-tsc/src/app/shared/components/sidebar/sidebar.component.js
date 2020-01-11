import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { Router, PRIMARY_OUTLET, NavigationEnd } from "@angular/router";
import { AuthService } from "@app/core/service";
import { MatDialog } from "@angular/material/dialog";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { LoginDialogComponent } from "../security/login-dialog/login-dialog.component";
import { RegisterDialogComponent } from "../security/register-dialog/register-dialog.component";
import { Subscription } from "rxjs";
import { UserService } from "@app/shared/service/user.service";
let SidebarComponent = class SidebarComponent {
    constructor(router, authService, dialog, dataSharingService, userService) {
        this.router = router;
        this.authService = authService;
        this.dialog = dialog;
        this.dataSharingService = dataSharingService;
        this.userService = userService;
        this.loginDialogOpened = false;
        this.registerDialogOpened = false;
        this.searchPF = false;
        this.searchMatches = false;
        this.searchPlayer = false;
        this.friends = false;
        this.incomingMatches = false;
        this.ownedPF = false;
        this.adminPanel = false;
        this.hideSidebar = true;
        this.userPanel = false;
        this.searchUser = false;
        this.subscription = new Subscription();
    }
    ngOnInit() {
        this.dataSharingService.isClosed.subscribe(val => {
            if (val) {
                this.loginDialogOpened = false;
                this.registerDialogOpened = false;
            }
        });
        this.setMenuActive();
        this.getLoggedUser();
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
    loginDialog() {
        this.dialog.open(LoginDialogComponent, {
            width: "250px",
            data: {}
        });
    }
    registerDialog() {
        this.dialog.open(RegisterDialogComponent, {
            width: "250px",
            data: {}
        });
    }
    isLogged() {
        return this.authService.isLogged();
    }
    logout() {
        this.authService.logout();
    }
    isAdmin() {
        return this.authService.isAdmin();
    }
    isWorker() {
        return this.authService.isWorker();
    }
    isUser() {
        return this.authService.isUser();
    }
    getLoggedUser() {
        if (this.isLogged()) {
            this.userService.getLoggedUser().subscribe(val => {
                if (val) {
                    this.loggedUser = val;
                }
            });
        }
    }
    changeSidebar() {
        this.hideSidebar = !this.hideSidebar;
    }
    navigateToSearchPlayingField() {
        this.reset();
        this.searchPF = true;
        this.router.navigate(["home"]);
    }
    navigateToSearchMatches() {
        this.reset();
        this.searchMatches = true;
        this.router.navigate(["findMatch"]);
    }
    navigateToSearchPlayer() {
        this.reset();
        this.searchPlayer = true;
        this.router.navigate(["search-user"]);
    }
    navigateToFriends() {
        this.reset();
        this.friends = true;
        this.router.navigate(["friends"]);
    }
    navigateToOwnedPF() {
        this.reset();
        this.ownedPF = true;
        this.router.navigate([
            "worker/playingField/" + this.loggedUser.playingFieldId
        ]);
    }
    navigateToAdminPanel() {
        this.reset();
        this.adminPanel = true;
        this.router.navigate(["admin-panel"]);
    }
    navigateToUserPanel() {
        this.reset();
        this.userPanel = true;
        this.dataSharingService.changeUser(localStorage.getItem("userId"));
        this.router.navigate(["users/" + localStorage.getItem("userId")]);
    }
    setMenuActive() {
        this.subscription.add(this.router.events.subscribe(val => {
            if (val instanceof NavigationEnd) {
                const tree = this.router.parseUrl(window.location.pathname);
                if (tree.root.children.primary &&
                    tree.root.children.primary.segments[2]) {
                    if (window.location.pathname ===
                        "/worker/playingField/" +
                            tree.root.children[PRIMARY_OUTLET].segments[2].path) {
                        this.reset();
                        this.ownedPF = true;
                    }
                }
                else if (tree.root.children.primary &&
                    tree.root.children.primary.segments[1]) {
                    if (window.location.pathname ===
                        "/users/" + tree.root.children[PRIMARY_OUTLET].segments[1].path) {
                        this.reset();
                        if (this.loggedUser &&
                            tree.root.children[PRIMARY_OUTLET].segments[1].path ===
                                this.loggedUser.id.toString()) {
                            this.userPanel = true;
                        }
                        else {
                            this.searchPlayer = true;
                        }
                    }
                }
                else if (window.location.pathname === "/search") {
                    this.reset();
                    this.searchPF = true;
                }
                else if (window.location.pathname === "/findMatch") {
                    this.reset();
                    this.searchMatches = true;
                }
                else if (window.location.pathname === "/findPlayer") {
                    this.reset();
                    this.searchPlayer = true;
                }
                else if (window.location.pathname === "/admin-panel") {
                    this.reset();
                    this.adminPanel = true;
                }
                else if (window.location.pathname === "/search-user") {
                    this.reset();
                    this.searchPlayer = true;
                }
            }
        }));
    }
    reset() {
        this.searchPF = false;
        this.searchMatches = false;
        this.searchPlayer = false;
        this.friends = false;
        this.incomingMatches = false;
        this.ownedPF = false;
        this.adminPanel = false;
        this.userPanel = false;
    }
};
SidebarComponent = tslib_1.__decorate([
    Component({
        selector: "app-sidebar",
        templateUrl: "./sidebar.component.html",
        styleUrls: ["./sidebar.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [Router,
        AuthService,
        MatDialog,
        DataSharingService,
        UserService])
], SidebarComponent);
export { SidebarComponent };
//# sourceMappingURL=sidebar.component.js.map