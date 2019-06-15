
import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import { LoginDialogComponent } from '@app/functionalities/home/components/login-dialog/login-dialog.component';
import { RegisterDialogComponent } from '@app/functionalities/home/components/register-dialog/register-dialog.component';
import { ReservationDialogComponent } from '@app/functionalities/home/components/reservation-dialog/reservation-dialog.component';
import { PlayingField } from '../model/google-map';
import { AssignPlayingFieldDialogComponent } from '@app/functionalities/home/components/assign-playing-field-dialog/assign-playing-field-dialog.component';
import { AssignPlayingFieldAndRegisterDialogComponent } from '@app/functionalities/home/components/assign-playing-field-and-register-dialog/assign-playing-field-and-register-dialog.component';
import { EnlargeImageDialogComponent } from '@app/functionalities/admin-panel/components/enlarge-image-dialog/enlarge-image-dialog.component';


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

  // openReservationDialog(pf: PlayingField) {
  //   this.dialog.open(ReservationDialogComponent, {
  //     width: '350px',
  //     data: {playingField: pf}
  //   });
  // }

  openAssignPFDialogDialog(playingField: PlayingField) {
    this.dialog.open(AssignPlayingFieldDialogComponent, {
      width: '350px',
      data: {playingField: playingField}
    });
  }
  openAssignPFAndRegisterDialogDialog(playingField: PlayingField) {
    this.dialog.open(AssignPlayingFieldAndRegisterDialogComponent, {
      width: '350px',
      data: {playingField: playingField}
    });
  }
  openEnlargeImageDialog(image: File) {
    this.dialog.open(EnlargeImageDialogComponent, {
      width: '350px',
      data: {image: image}
    });
  }
}
