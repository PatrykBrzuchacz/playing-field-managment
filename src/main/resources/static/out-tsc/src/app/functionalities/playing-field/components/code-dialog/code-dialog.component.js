import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
let CodeDialogComponent = class CodeDialogComponent {
    constructor(data, dialogRef) {
        this.data = data;
        this.dialogRef = dialogRef;
    }
    ngOnInit() { }
    Ok() {
        this.dialogRef.close();
    }
};
CodeDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-code-dialog",
        templateUrl: "./code-dialog.component.html",
        styleUrls: ["./code-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef])
], CodeDialogComponent);
export { CodeDialogComponent };
//# sourceMappingURL=code-dialog.component.js.map