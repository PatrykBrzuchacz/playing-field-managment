import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { PlayingFieldComponent } from './components/playing-field.component';
import { PlayingFieldRoutingModule } from './playing-field-routing.module';
import { SharedModule } from '@app/shared/shared.module';
let PlayingFieldModule = class PlayingFieldModule {
};
PlayingFieldModule = tslib_1.__decorate([
    NgModule({
        declarations: [PlayingFieldComponent],
        imports: [
            PlayingFieldRoutingModule,
            SharedModule,
        ],
    })
], PlayingFieldModule);
export { PlayingFieldModule };
//# sourceMappingURL=playing-field.module.js.map