import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared/shared.module';
import { SearchUserRoutingModule } from './search-user.routing.module';
import { SearchUserComponent } from './search-user/search-user.component';
import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { GeoLocationService } from '@app/shared/service/geo-location.service';
import { GoogleService } from '@app/shared/service/google.service';
let SearchUserModule = class SearchUserModule {
};
SearchUserModule = tslib_1.__decorate([
    NgModule({
        declarations: [SearchUserComponent],
        imports: [
            CommonModule,
            SharedModule,
            SearchUserRoutingModule,
            AgmCoreModule.forRoot({
                apiKey: 'AIzaSyC7Iy1oeCLfim7-B5c_Aro6pTr_oFifBGM'
            })
        ],
        providers: [
            GoogleMapsAPIWrapper,
            GeoLocationService,
            GoogleService
        ],
    })
], SearchUserModule);
export { SearchUserModule };
//# sourceMappingURL=search-user.module.js.map