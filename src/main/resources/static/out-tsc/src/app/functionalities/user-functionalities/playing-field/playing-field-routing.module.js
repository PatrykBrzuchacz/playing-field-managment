import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PlayingFieldComponent } from './components/playing-field/playing-field.component';
var routes = [
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
var PlayingFieldRoutingModule = /** @class */ (function () {
    function PlayingFieldRoutingModule() {
    }
    PlayingFieldRoutingModule = tslib_1.__decorate([
        NgModule({
            imports: [RouterModule.forChild(routes)],
            exports: [RouterModule]
        })
    ], PlayingFieldRoutingModule);
    return PlayingFieldRoutingModule;
}());
export { PlayingFieldRoutingModule };
//# sourceMappingURL=playing-field-routing.module.js.map