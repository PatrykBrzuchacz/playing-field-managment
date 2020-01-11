import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { MainPageComponent } from './components/main-page/main-page.component';
import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '@app/shared/shared.module';
import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { GoogleService } from '@app/shared/service/google.service';
import { GeoLocationService } from '@app/shared/service/geo-location.service';
import { AssignPlayingFieldAndRegisterDialogComponent } from './components/assign-playing-field-and-register-dialog/assign-playing-field-and-register-dialog.component';
let HomeModule = class HomeModule {
};
HomeModule = tslib_1.__decorate([
    NgModule({
        declarations: [
            MainPageComponent,
            AssignPlayingFieldAndRegisterDialogComponent,
        ],
        imports: [
            SharedModule,
            HomeRoutingModule,
            AgmCoreModule.forRoot({
                apiKey: 'AIzaSyC7Iy1oeCLfim7-B5c_Aro6pTr_oFifBGM'
            })
        ],
        providers: [
            GoogleMapsAPIWrapper,
            GeoLocationService,
            GoogleService
        ],
        entryComponents: [
            AssignPlayingFieldAndRegisterDialogComponent
        ]
    })
], HomeModule);
export { HomeModule };
//# sourceMappingURL=home.module.js.map