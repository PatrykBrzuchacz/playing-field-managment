import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RouterModule } from '@angular/router';
const routes = [
    {
        path: '',
        component: MainPageComponent
    }
];
let HomeRoutingModule = class HomeRoutingModule {
};
HomeRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], HomeRoutingModule);
export { HomeRoutingModule };
//# sourceMappingURL=home-routing.module.js.map