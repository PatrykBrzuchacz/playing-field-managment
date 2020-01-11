import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "./auth.service";
let AuthGuardService = class AuthGuardService {
    constructor(auth, router) {
        this.auth = auth;
        this.router = router;
    }
    canActivate() {
        if (!this.auth.isLogged()) {
            this.router.navigate(["/search"]);
            return false;
        }
        return true;
    }
    canLoad() {
        if (!this.auth.isAdmin()) {
            this.router.navigate(["/search"]);
            return false;
        }
        else
            return true;
    }
};
AuthGuardService = tslib_1.__decorate([
    Injectable(),
    tslib_1.__metadata("design:paramtypes", [AuthService, Router])
], AuthGuardService);
export { AuthGuardService };
//# sourceMappingURL=auth-guard.service.js.map