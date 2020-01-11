import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchUserComponent } from './search-user/search-user.component';
const routes = [
    {
        path: '',
        pathMatch: 'full',
        component: SearchUserComponent
    }
];
let SearchUserRoutingModule = class SearchUserRoutingModule {
};
SearchUserRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], SearchUserRoutingModule);
export { SearchUserRoutingModule };
//# sourceMappingURL=search-user.routing.module.js.map