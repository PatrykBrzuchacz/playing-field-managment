import * as tslib_1 from "tslib";
import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { RegisterWorkerService } from '@app/shared/service/register-worker.service';
import { AuthService } from '@app/core/service';
let AssignPlayingFieldAndRegisterDialogComponent = class AssignPlayingFieldAndRegisterDialogComponent {
    constructor(dialogRef, data, toastr, registerWorkerService, authService) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.toastr = toastr;
        this.registerWorkerService = registerWorkerService;
        this.authService = authService;
        this.sendError = false;
        this.hidingPassword = true;
        this.hidingPasswordConfirm = true;
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
            passwordConfirm: new FormControl(null, Validators.required)
        });
    }
    onFileImport(event) {
        this.createImageFromBlob(event[0]);
        this.selectedFile = event[0];
    }
    createImageFromBlob(image) {
        let reader = new FileReader();
        reader.addEventListener("load", () => {
            this.image = reader.result;
        }, false);
        if (image) {
            reader.readAsDataURL(image);
        }
    }
    sendRequestWhenLoggedIn() {
        this.registerWorkerService
            .sendRequestToAssignPF(this.data.playingField, this.selectedFile)
            .subscribe(success => { }, error => {
            this.toastr.error('Wystąpił błąd');
        }, () => {
            this.dialogRef.close();
            this.toastr.success('Prośba została wysłana, teraz musi Pan/i poczekać na weryfikację, dziękujemy');
        });
    }
    sendRequest() {
        this.registerWorkerService
            .sendRequestToAssignPFAndRegister(this.data.playingField, this.selectedFile, this.registerForm.value.login, this.registerForm.value.password)
            .subscribe(success => { }, error => {
            this.toastr.error('Wystąpił błąd');
        }, () => {
            this.dialogRef.close();
            this.toastr.success('Prośba została wysłana, dziękujemy');
        });
    }
    isLogged() {
        return this.authService.isLogged();
    }
    isWorker() {
        return this.authService.isWorker();
    }
};
AssignPlayingFieldAndRegisterDialogComponent = tslib_1.__decorate([
    Component({
        selector: 'app-assign-playing-field-and-register-dialog',
        templateUrl: './assign-playing-field-and-register-dialog.component.html',
        styleUrls: ['./assign-playing-field-and-register-dialog.component.scss']
    }),
    tslib_1.__param(1, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [MatDialogRef, Object, ToastrService,
        RegisterWorkerService,
        AuthService])
], AssignPlayingFieldAndRegisterDialogComponent);
export { AssignPlayingFieldAndRegisterDialogComponent };
//# sourceMappingURL=assign-playing-field-and-register-dialog.component.js.map