import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { UserManagmentComponent } from './components/user-managment/user-managment.component';
const routes = [
    {
        path: '',
        component: AdminLayoutComponent,
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'users'
            },
            {
                path: 'users',
                component: UserManagmentComponent,
            }
        ]
    }
];
let AdminPanelRoutingModule = class AdminPanelRoutingModule {
};
AdminPanelRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], AdminPanelRoutingModule);
export { AdminPanelRoutingModule };
//# sourceMappingURL=admin-panel-routing.module.js.map