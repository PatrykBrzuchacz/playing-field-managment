import * as tslib_1 from "tslib";
import { Component } from "@angular/core";
import { FormControl, Validators } from "@angular/forms";
import { MatchService } from "@app/shared/service/match.service";
import { ToastrService } from "ngx-toastr";
import { MatDialogRef } from "@angular/material";
let WorkerAddCodeDialogComponent = class WorkerAddCodeDialogComponent {
    constructor(matchService, dialogRef, toastrService) {
        this.matchService = matchService;
        this.dialogRef = dialogRef;
        this.toastrService = toastrService;
        this.code = new FormControl("", Validators.required);
    }
    ngOnInit() {
        this.dialogRef.beforeClosed().subscribe(() => {
            window.location.reload();
        });
    }
    addCode() {
        this.matchService.fillCodeByCode(this.code.value).subscribe(val => {
            this.toastrService.success("Pomyślnie dodano kod");
        }, error => {
            if (error.status === 409) {
                this.toastrService.error("Kod został już wpisany");
            }
            else {
                this.toastrService.error("Kod jest niepoprawny");
            }
        });
    }
};
WorkerAddCodeDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-worker-add-code-dialog",
        templateUrl: "./worker-add-code-dialog.component.html",
        styleUrls: ["./worker-add-code-dialog.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [MatchService,
        MatDialogRef,
        ToastrService])
], WorkerAddCodeDialogComponent);
export { WorkerAddCodeDialogComponent };
//# sourceMappingURL=worker-add-code-dialog.component.js.map