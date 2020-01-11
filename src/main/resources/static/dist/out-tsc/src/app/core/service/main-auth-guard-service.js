import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthService } from "./auth.service";
let MainAuthGuardService = class MainAuthGuardService {
    constructor(auth, router, activatedRouter) {
        this.auth = auth;
        this.router = router;
        this.activatedRouter = activatedRouter;
    }
    canActivate() {
        if (this.auth.isLogged() && window.location.href === "http://localhost:4200/") {
            this.router.navigate(["/users/" + localStorage.getItem('userId')]);
            return false;
        }
        else {
            return true;
        }
    }
};
MainAuthGuardService = tslib_1.__decorate([
    Injectable(),
    tslib_1.__metadata("design:paramtypes", [AuthService, Router, ActivatedRoute])
], MainAuthGuardService);
export { MainAuthGuardService };
5;
//# sourceMappingURL=main-auth-guard-service.js.map