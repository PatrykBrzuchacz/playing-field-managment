import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainPageComponent } from './components/main-page/main-page.component';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { RegisterDialogComponent } from './components/register-dialog/register-dialog.component';
import { NavbarComponent } from './components/navbar/navbar.component';

import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '@app/shared/shared.module';
import { DialogService } from '@app/shared/service/dialog.service';
import { ReservationDialogComponent } from './components/reservation-dialog/reservation-dialog.component';
import { LayoutComponent } from './components/layout/layout.component';
import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { GoogleService } from '@app/shared/service/google.service';
import { GeoLocationService } from '@app/shared/service/geo-location.service';


@NgModule({
  declarations: 
  [
    MainPageComponent,
    LoginDialogComponent,
    RegisterDialogComponent,
    NavbarComponent,
    ReservationDialogComponent,
    LayoutComponent
  ],

  imports: [
    SharedModule,
    HomeRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyC7Iy1oeCLfim7-B5c_Aro6pTr_oFifBGM'
    }),
  ],
  providers: [
    DialogService,
    GoogleMapsAPIWrapper,
    GeoLocationService,
    GoogleService
  ],
  entryComponents: [
    LoginDialogComponent,
    RegisterDialogComponent,
    ReservationDialogComponent

  ]
})
export class HomeModule { }
