import * as tslib_1 from "tslib";
import { NgModule } from "@angular/core";
import { WorkerPFMenuComponent } from "./worker-pfmenu/worker-pfmenu.component";
import { SharedModule } from "@app/shared/shared.module";
import { WorkerRoutingModule } from "./worker-routing.module";
import { WorkerPFDatePickerComponent } from "./worker-pfdate-picker/worker-pfdate-picker.component";
import { WorkerPFSetupDialogComponent } from "./worker-pfsetup-dialog/worker-pfsetup-dialog.component";
import { WorkerAddCodeDialogComponent } from "./worker-add-code-dialog/worker-add-code-dialog.component";
let WorkerModule = class WorkerModule {
};
WorkerModule = tslib_1.__decorate([
    NgModule({
        declarations: [
            WorkerPFMenuComponent,
            WorkerPFDatePickerComponent,
            WorkerPFSetupDialogComponent,
            WorkerAddCodeDialogComponent
        ],
        imports: [SharedModule, WorkerRoutingModule],
        providers: [],
        entryComponents: [
            WorkerAddCodeDialogComponent,
            WorkerPFDatePickerComponent,
            WorkerPFSetupDialogComponent
        ]
    })
], WorkerModule);
export { WorkerModule };
//# sourceMappingURL=worker.module.js.map