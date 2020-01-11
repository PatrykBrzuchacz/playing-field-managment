import * as tslib_1 from "tslib";
import { NgModule } from "@angular/core";
import { SearchMatchComponent } from "./search-match/search-match.component";
import { SearchMatchRoutingModule } from "./search-match-routing.module";
import { SharedModule } from "@app/shared/shared.module";
import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { GeoLocationService } from '@app/shared/service/geo-location.service';
import { GoogleService } from '@app/shared/service/google.service';
let SearchMatchModule = class SearchMatchModule {
};
SearchMatchModule = tslib_1.__decorate([
    NgModule({
        declarations: [SearchMatchComponent],
        imports: [
            SearchMatchRoutingModule,
            SharedModule,
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
], SearchMatchModule);
export { SearchMatchModule };
//# sourceMappingURL=search-match.module.js.map