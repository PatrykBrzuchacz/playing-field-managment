import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import { ToastrService } from 'ngx-toastr';
import { RegisterWorkerService } from '@app/shared/service/register-worker.service';
import { PlayingFieldDetails } from '@app/shared/model/google-map';

@Component({
  selector: 'app-assign-playing-field-dialog',
  templateUrl: './assign-playing-field-dialog.component.html',
  styleUrls: ['./assign-playing-field-dialog.component.scss']
})
export class AssignPlayingFieldDialogComponent implements OnInit {

  // public reservationForm: FormGroup;
  sendError = false;
  selectedFile: File;


  constructor(
    private dialogRef: MatDialogRef<AssignPlayingFieldDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService,
    private registerWorkerService: RegisterWorkerService) {
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
    this.registerWorkerService.sendRequestToAssignPF(this.data.playingField, this.selectedFile).subscribe(success => {
      this.dialogRef.close();
      this.toastr.success('Prośba została wysłana, dziękujemy');
    }, error => {
      this.toastr.error('Wystąpił błąd');
    });
  }

}
