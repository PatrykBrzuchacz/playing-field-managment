import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "./auth.service";
import { Subscription } from "rxjs";
let AuthGuardWorkerService = class AuthGuardWorkerService {
    constructor(auth, router) {
        this.auth = auth;
        this.router = router;
        this.subscription = new Subscription();
    }
    canActivate() {
        if (!this.auth.isLogged()) {
            this.router.navigate(["/search"]);
            return false;
        }
        return true;
    }
    canLoad(router, segments) {
        if (!this.auth.isLogged()) {
            this.router.navigate(["/search"]);
            return false;
        }
        else {
            if (segments[2].path === localStorage.getItem("playingFieldId")) {
                return true;
            }
            else {
                this.router.navigate(["/search"]);
                return false;
            }
        }
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
};
AuthGuardWorkerService = tslib_1.__decorate([
    Injectable(),
    tslib_1.__metadata("design:paramtypes", [AuthService, Router])
], AuthGuardWorkerService);
export { AuthGuardWorkerService };
//# sourceMappingURL=auth-guard-worker.service.js.map