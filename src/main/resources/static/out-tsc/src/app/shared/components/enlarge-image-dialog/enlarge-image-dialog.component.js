import * as tslib_1 from "tslib";
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
let EnlargeImageDialogComponent = class EnlargeImageDialogComponent {
    constructor(data) {
        this.data = data;
    }
    ngOnInit() {
        this.image = this.data.image;
    }
};
EnlargeImageDialogComponent = tslib_1.__decorate([
    Component({
        selector: 'app-enlarge-image-dialog',
        templateUrl: './enlarge-image-dialog.component.html',
        styleUrls: ['./enlarge-image-dialog.component.scss']
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object])
], EnlargeImageDialogComponent);
export { EnlargeImageDialogComponent };
//# sourceMappingURL=enlarge-image-dialog.component.js.map