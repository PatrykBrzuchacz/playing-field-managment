import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { PlayingFieldSetupDto } from "@app/shared/model/playing-field";
import { ToastrService } from "ngx-toastr";
import { DataSharingService } from "@app/shared/service/data-sharing.service";
import { WorkerService } from "@app/shared/service/worker.service";
import { DomSanitizer } from "@angular/platform-browser";
let WorkerPFSetupDialogComponent = class WorkerPFSetupDialogComponent {
    constructor(data, dialogRef, workerService, toastrService, dataSharingService, domSanitizer) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.workerService = workerService;
        this.toastrService = toastrService;
        this.dataSharingService = dataSharingService;
        this.domSanitizer = domSanitizer;
    }
    ngOnInit() {
        this.initForm();
    }
    initForm() {
        if (this.data.playingFieldSetupDto) {
            this.pfSetupForm = new FormGroup({
                description: new FormControl(this.data.playingFieldSetupDto.description),
                teamSize: new FormControl(this.data.playingFieldSetupDto.teamSize),
                name: new FormControl(this.data.playingFieldSetupDto.name)
            });
            this.image = this.data.image;
            this.pfPhoto = this.data.playingFieldSetupDto.pfPhoto;
        }
        else {
            this.pfSetupForm = new FormGroup({
                description: new FormControl(""),
                teamSize: new FormControl(),
                name: new FormControl()
            });
        }
    }
    onFileImport(event) {
        this.createImageFromBlob(event[0]);
        this.pfPhoto = event[0];
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
    setPFSetup() {
        if (this.data && this.data.playingFieldSetupDto && this.pfPhoto === this.data.playingFieldSetupDto.pfPhoto) {
            this.pfPhoto = new File([this.data.playingFieldSetupDto.pfPhoto], this.data.playingFieldSetupDto.pfPhoto.name);
        }
        const pfSetup = new PlayingFieldSetupDto(this.pfSetupForm.value.description, this.pfSetupForm.value.teamSize, this.pfPhoto, this.pfSetupForm.value.name);
        this.workerService.setPFSetup(this.data.pfId, pfSetup).subscribe(() => { }, () => this.toastrService.error("Nie udało się zaktualizować danych orliku"), () => {
            this.toastrService.success("Pomyślnie zaktualizowano dane orlika");
            this.dataSharingService.name.next(this.pfSetupForm.value.name);
            this.dialogRef.close({ pfSetup: pfSetup, image: this.image });
        });
    }
    removeFile() {
        this.pfPhoto = null;
    }
};
WorkerPFSetupDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-worker-pfsetup-dialog",
        templateUrl: "./worker-pfsetup-dialog.component.html",
        styleUrls: ["./worker-pfsetup-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        WorkerService,
        ToastrService,
        DataSharingService,
        DomSanitizer])
], WorkerPFSetupDialogComponent);
export { WorkerPFSetupDialogComponent };
//# sourceMappingURL=worker-pfsetup-dialog.component.js.map