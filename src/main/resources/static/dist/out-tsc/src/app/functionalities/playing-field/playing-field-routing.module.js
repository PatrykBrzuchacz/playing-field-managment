import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PlayingFieldComponent } from './components/playing-field.component';
const routes = [
    {
        path: '',
        pathMatch: 'full',
        component: PlayingFieldComponent,
        children: [
        // {
        //   path: '',
        //   pathMatch: 'full'
        // }
        ]
    }
];
let PlayingFieldRoutingModule = class PlayingFieldRoutingModule {
};
PlayingFieldRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forChild(routes)],
        exports: [RouterModule]
    })
], PlayingFieldRoutingModule);
export { PlayingFieldRoutingModule };
//# sourceMappingURL=playing-field-routing.module.js.map