import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.scss']
})
export class ReservationDialogComponent implements OnInit {

  // public reservationForm: FormGroup;
  sendError = false;
  selectedFile: File;


  constructor(
    private dialogRef: MatDialogRef<ReservationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService) {
  }

  ngOnInit() {
    // this.reservationForm = new FormGroup({
    //   numberOfPersons: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(1000)]),
    //   date: new FormControl(null, [Validators.required]),
    //   clientMessage: new FormControl(null)
    // });
  }
  processFile(event){
    this.selectedFile = event.target.files[0]
  }
  sendRequest() {

  }

}
