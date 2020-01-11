import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormGroup, FormControl } from "@angular/forms";
import { UserService } from "@app/shared/service/user.service";
import { EditUserDto, Position } from "@app/shared/model";
import { ToastrService } from "ngx-toastr";
import { of } from "rxjs";
import { LocationService } from "@app/shared/service/location.service";
import { switchMap, map, catchError, tap } from "rxjs/operators";
import { GeoLocationService } from "@app/shared/service/geo-location.service";
let EditUserDataComponent = class EditUserDataComponent {
    constructor(data, dialogRef, userService, toastrService, locationService, geolocationService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.userService = userService;
        this.toastrService = toastrService;
        this.locationService = locationService;
        this.geolocationService = geolocationService;
        this.handleSelectedLocationChange = () => {
            return this.userForm.controls.city.valueChanges.pipe(switchMap((address) => {
                return this.geolocationService.getPositions(address);
            }), map((data) => {
                return data && data;
            }), catchError((error) => {
                return of([]);
            }), tap((data) => {
                this.searchedLocations = data;
            }));
        };
    }
    ngOnInit() {
        this.initForm();
        this.avatar = this.data.avatar;
        this.newAvatar = this.data.user.avatar;
        this.locations = this.handleSelectedLocationChange();
    }
    initForm() {
        this.userForm = new FormGroup({
            firstName: new FormControl(this.data.user.firstName),
            lastName: new FormControl(this.data.user.lastName),
            age: new FormControl(this.data.user.age),
            city: new FormControl(this.data.user.city),
            position: new FormControl(this.data.user.position),
            email: new FormControl(this.data.user.email),
            phoneNumber: new FormControl(this.data.user.phoneNumber)
        });
    }
    onFileImport(event) {
        this.createImageFromBlob(event[0]);
        this.newAvatar = event[0];
    }
    createImageFromBlob(image) {
        let reader = new FileReader();
        reader.addEventListener("load", () => {
            this.avatar = reader.result;
        }, false);
        if (image) {
            reader.readAsDataURL(image);
        }
    }
    editUser() {
        if (this.data &&
            this.data.user &&
            this.newAvatar === this.data.user.avatar &&
            this.newAvatar) {
            this.newAvatar = new File([this.dataURItoBlob(this.data.user.avatar)], "avatar");
        }
        const formData = this.userForm.value;
        let position;
        if (formData.position === "Bramkarz") {
            position = Position.Bramkarz;
        }
        else if (formData.position === "Obrońca") {
            position = Position.Obrońca;
        }
        else if (formData.position === "Pomocnik") {
            position = Position.Pomocnik;
        }
        else if (formData.position === "Napastnik") {
            position = Position.Napastnik;
        }
        else
            position = "";
        let editUserDto = new EditUserDto(this.data.user.id, formData.age, formData.phoneNumber, this.newAvatar, position, formData.city, formData.firstName, formData.lastName, formData.email);
        this.userService.editUser(editUserDto).subscribe(() => {
            this.dialogRef.close({ user: editUserDto, avatar: this.avatar });
        }, () => this.toastrService.error("Nie udało się zaktualizować danych"));
    }
    dataURItoBlob(dataURI) {
        const byteString = dataURI;
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: "image/jpeg" });
        return blob;
    }
};
EditUserDataComponent = tslib_1.__decorate([
    Component({
        selector: "app-edit-user-data",
        templateUrl: "./edit-user-data.component.html",
        styleUrls: ["./edit-user-data.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        UserService,
        ToastrService,
        LocationService,
        GeoLocationService])
], EditUserDataComponent);
export { EditUserDataComponent };
//# sourceMappingURL=edit-user-data.component.js.map