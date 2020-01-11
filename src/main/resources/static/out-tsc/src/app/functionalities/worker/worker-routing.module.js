import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { WorkerPFMenuComponent } from './worker-pfmenu/worker-pfmenu.component';
import { RouterModule } from '@angular/router';
const routes = [
    {
        path: '',
        pathMatch: 'full',
        component: WorkerPFMenuComponent,
        children: [
        // {
        //   path: '',
        //   pathMatch: 'full'
        // }
        ]
    }
];
let WorkerRoutingModule = class WorkerRoutingModule {
};
WorkerRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], WorkerRoutingModule);
export { WorkerRoutingModule };
//# sourceMappingURL=worker-routing.module.js.map