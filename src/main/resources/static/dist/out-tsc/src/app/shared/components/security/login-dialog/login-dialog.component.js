import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { Router } from "@angular/router";
let LoginDialogComponent = class LoginDialogComponent {
    constructor(authService, dialogRef, toastr, userService, router) {
        this.authService = authService;
        this.dialogRef = dialogRef;
        this.toastr = toastr;
        this.userService = userService;
        this.router = router;
        this.onSuccess = () => {
            this.userService.getLoggedUser().subscribe(val => {
                localStorage.setItem("userId", val.id.toString());
                if (val.playingFieldId) {
                    localStorage.setItem("playingFieldId", val.playingFieldId.toString());
                }
                this.router.navigate(["/users/" + val.id]);
            }, () => { }, () => {
                window.location.reload();
            });
            this.dialogRef.close();
        };
        this.onFail = (error) => {
            if (error.status === 403) {
                this.toastr.error("Podano nieprawidłowe dane", "Błąd");
            }
        };
    }
    ngOnInit() {
        this.loginFailed = false;
        this.loginForm = new FormGroup({
            login: new FormControl(null),
            password: new FormControl(null)
        });
    }
    login() {
        this.authService
            .login(this.getUserCredentials())
            .subscribe(this.onSuccess, this.onFail);
    }
    getUserCredentials() {
        const formValue = this.loginForm.value;
        return {
            username: formValue.login,
            password: formValue.password
        };
    }
};
LoginDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-login-dialog",
        templateUrl: "./login-dialog.component.html",
        styleUrls: ["./login-dialog.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [AuthService,
        MatDialogRef,
        ToastrService,
        UserService,
        Router])
], LoginDialogComponent);
export { LoginDialogComponent };
//# sourceMappingURL=login-dialog.component.js.map