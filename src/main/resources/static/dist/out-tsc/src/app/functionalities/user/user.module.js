import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserPanelComponent } from './user-panel/user-panel.component';
import { EditUserDataComponent } from './edit-user-data/edit-user-data.component';
import { UserRoutingModule } from './user-routing.module';
import { SharedModule } from '@app/shared/shared.module';
import { EnlargeImageDialogComponent } from '@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component';
import { GoogleMapsAPIWrapper, AgmCoreModule } from '@agm/core';
import { GeoLocationService } from '@app/shared/service/geo-location.service';
import { GoogleService } from '@app/shared/service/google.service';
let UserModule = class UserModule {
};
UserModule = tslib_1.__decorate([
    NgModule({
        declarations: [UserPanelComponent, EditUserDataComponent],
        imports: [
            CommonModule,
            UserRoutingModule,
            SharedModule,
            AgmCoreModule.forRoot({
                apiKey: 'AIzaSyC7Iy1oeCLfim7-B5c_Aro6pTr_oFifBGM'
            })
        ],
        entryComponents: [EditUserDataComponent, EnlargeImageDialogComponent],
        providers: [
            GoogleMapsAPIWrapper,
            GeoLocationService,
            GoogleService
        ],
    })
], UserModule);
export { UserModule };
//# sourceMappingURL=user.module.js.map