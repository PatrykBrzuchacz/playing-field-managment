import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchMatchComponent } from './search-match/search-match.component';
const routes = [
    {
        path: '',
        pathMatch: 'full',
        component: SearchMatchComponent
    }
];
let SearchMatchRoutingModule = class SearchMatchRoutingModule {
};
SearchMatchRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], SearchMatchRoutingModule);
export { SearchMatchRoutingModule };
//# sourceMappingURL=search-match-routing.module.js.map