import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { FormGroup, Validators, FormControl } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { AuthService } from "@app/core/service";
import { UserCredentials, Position } from "@app/shared/model";
import { ToastrService } from 'ngx-toastr';
let RegisterDialogComponent = class RegisterDialogComponent {
    constructor(authService, dialogRef, toastr) {
        this.authService = authService;
        this.dialogRef = dialogRef;
        this.toastr = toastr;
        this.hidingPassword = true;
        this.hidingPasswordConfirm = true;
        this.isWorker = false;
        this.positions = ["Bramkarz", "Obrońca", "Pomocnik", "Napastnik"];
        this.onFail = (error) => {
            if (error.status === 409) {
                this.toastr.error('Użytkownik z podaną nazwą użytkownika już istnieje');
            }
        };
        this.onSuccess = () => {
            this.toastr.success('Dziękujemy za rejestrację');
            this.dialogRef.close();
        };
    }
    ngOnInit() {
        this.registerForm = new FormGroup({
            login: new FormControl(null, [
                Validators.required,
                Validators.minLength(4)
            ]),
            password: new FormControl(null, [
                Validators.required,
                Validators.minLength(6)
            ]),
            passwordConfirm: new FormControl(null, Validators.required),
            position: new FormControl(null, Validators.required)
        });
    }
    processFile(event) {
        this.selectedFile = event.target.files[0];
    }
    register() {
        this.authService
            .register(this.getRegistrationDetails())
            .subscribe(this.onSuccess, this.onFail);
    }
    getRegistrationDetails() {
        const formValue = this.registerForm.value;
        const userCredentials = new UserCredentials(formValue.login, formValue.password);
        let position;
        if (formValue.position === "Bramkarz") {
            position = Position.Bramkarz;
        }
        else if (formValue.position === "Obrońca") {
            position = Position.Obrońca;
        }
        else if (formValue.position === "Pomocnik") {
            position = Position.Pomocnik;
        }
        else {
            position = Position.Napastnik;
        }
        return {
            userCredentials: userCredentials,
            position: position
        };
    }
};
RegisterDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-register-dialog",
        templateUrl: "./register-dialog.component.html",
        styleUrls: ["./register-dialog.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [AuthService,
        MatDialogRef,
        ToastrService])
], RegisterDialogComponent);
export { RegisterDialogComponent };
//# sourceMappingURL=register-dialog.component.js.map