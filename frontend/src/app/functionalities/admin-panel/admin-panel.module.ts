import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagmentComponent } from './components/user-managment/user-managment.component';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { SharedModule } from '@app/shared/shared.module';
import { AdminPanelRoutingModule } from './admin-panel-routing.module';
import { EnlargeImageDialogComponent } from './components/enlarge-image-dialog/enlarge-image-dialog.component';

@NgModule({
  declarations: [UserManagmentComponent, AdminLayoutComponent, EnlargeImageDialogComponent],
  imports: [
    SharedModule,
    AdminPanelRoutingModule,

  ],
  providers: [

  ],
  entryComponents: [
    EnlargeImageDialogComponent
  ]
})
export class AdminPanelModule { }
