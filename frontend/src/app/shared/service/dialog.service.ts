
import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import { LoginDialogComponent } from '@app/functionalities/home/components/login-dialog/login-dialog.component';
import { RegisterDialogComponent } from '@app/functionalities/home/components/register-dialog/register-dialog.component';
import { ReservationDialogComponent } from '@app/functionalities/home/components/reservation-dialog/reservation-dialog.component';


@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(public dialog: MatDialog) {
  }

  openLoginDialog() {
    this.dialog.open(LoginDialogComponent, {
      width: '250px',
      data: {}
    });
  }

  openRegisterDialog() {
    this.dialog.open(RegisterDialogComponent, {
      width: '250px',
      data: {}
    });
  }

  closeAllDialogs() {
    this.dialog.closeAll();
  }
  
  openReservationDialog(restaurantApiId: string) {
    this.dialog.open(ReservationDialogComponent, {
      width: '350px',
      data: {restaurantApiId: restaurantApiId}
    });
  }
}
