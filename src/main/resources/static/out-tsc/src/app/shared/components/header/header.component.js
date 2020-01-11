import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { Router, NavigationEnd, PRIMARY_OUTLET } from "@angular/router";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { Subscription } from "rxjs";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
let HeaderComponent = class HeaderComponent {
    constructor(router, dataSharingService, authService, userService) {
        this.router = router;
        this.dataSharingService = dataSharingService;
        this.authService = authService;
        this.userService = userService;
        this.subscription = new Subscription();
        this.activeNotifications = false;
    }
    ngOnInit() {
        this.isLogged();
        this.getLoggedUser();
        this.subscription.add(this.router.events.subscribe(val => {
            if (val instanceof NavigationEnd) {
                const tree = this.router.parseUrl(window.location.pathname);
                if (tree.root.children.primary &&
                    tree.root.children.primary.segments[2]) {
                    if (window.location.pathname ===
                        "/worker/playingField/" +
                            tree.root.children[PRIMARY_OUTLET].segments[2].path) {
                        this.dataSharingService.name.subscribe(val => (this.headerName = val));
                    }
                }
                else if (tree.root.children.primary &&
                    tree.root.children.primary.segments[1]) {
                    if (window.location.pathname ===
                        "/playingField/" +
                            tree.root.children[PRIMARY_OUTLET].segments[1].path) {
                        this.dataSharingService.name.subscribe(val => (this.headerName = val));
                    }
                    else if (window.location.pathname ===
                        "/users/" + tree.root.children[PRIMARY_OUTLET].segments[1].path) {
                        this.userService
                            .getUserUsername(tree.root.children[PRIMARY_OUTLET].segments[1].path)
                            .subscribe(val => {
                            this.headerName = val.username;
                        });
                    }
                }
                else if (window.location.pathname === "/search") {
                    this.headerName = "Wyszukaj orlik";
                }
                else if (window.location.pathname === "/admin-panel/users") {
                    this.headerName = "Panel admina";
                }
                else if (window.location.pathname === "/friends") {
                    this.headerName = "Znajomi";
                }
                else if (window.location.pathname === "/findMatch") {
                    this.headerName = "Wyszukaj mecz";
                }
                else if (window.location.pathname === "/search-user") {
                    this.headerName = "Wyszukaj użytkowników";
                }
            }
        }));
        this.username = localStorage.getItem("username");
    }
    getLoggedUser() {
        if (this.authService.isLogged()) {
            this.userService.getLoggedUser().subscribe(val => {
                this.loggedUser = val;
            });
        }
    }
    logout() {
        this.authService.logout();
    }
    isLogged() {
        this.isLoggedField = this.authService.isLogged();
    }
    toggleActive() {
        this.activeNotifications = !this.activeNotifications;
        console.log(this.activeNotifications);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
};
HeaderComponent = tslib_1.__decorate([
    Component({
        selector: "app-header",
        templateUrl: "./header.component.html",
        styleUrls: ["./header.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [Router,
        DataSharingService,
        AuthService,
        UserService])
], HeaderComponent);
export { HeaderComponent };
//# sourceMappingURL=header.component.js.map