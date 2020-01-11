import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { UserManagmentComponent } from './components/user-managment/user-managment.component';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { SharedModule } from '@app/shared/shared.module';
import { AdminPanelRoutingModule } from './admin-panel-routing.module';
import { EnlargeImageDialogComponent } from '../../shared/components/enlarge-image-dialog/enlarge-image-dialog.component';
let AdminPanelModule = class AdminPanelModule {
};
AdminPanelModule = tslib_1.__decorate([
    NgModule({
        declarations: [UserManagmentComponent, AdminLayoutComponent],
        imports: [
            SharedModule,
            AdminPanelRoutingModule,
        ],
        providers: [],
        entryComponents: [
            EnlargeImageDialogComponent
        ]
    })
], AdminPanelModule);
export { AdminPanelModule };
//# sourceMappingURL=admin-panel.module.js.map