import * as tslib_1 from "tslib";
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { BanService } from '@app/shared/service/ban.service';
let WorkerBanDialogComponent = class WorkerBanDialogComponent {
    constructor(data, dialogRef, banService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.banService = banService;
    }
    ngOnInit() {
    }
    banUser() {
        this.banService.banUser(this.data.pfId, this.data.userId).subscribe((val) => {
            this.dialogRef.close({ banDto: val });
        });
    }
    cancel() {
        this.dialogRef.close();
    }
};
WorkerBanDialogComponent = tslib_1.__decorate([
    Component({
        selector: 'app-worker-ban-dialog',
        templateUrl: './worker-ban-dialog.component.html',
        styleUrls: ['./worker-ban-dialog.component.scss']
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        BanService])
], WorkerBanDialogComponent);
export { WorkerBanDialogComponent };
//# sourceMappingURL=worker-ban-dialog.component.js.map