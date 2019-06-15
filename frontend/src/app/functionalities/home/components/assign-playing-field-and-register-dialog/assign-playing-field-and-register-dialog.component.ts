import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import { ToastrService } from 'ngx-toastr';
import { RegisterWorkerService } from '@app/shared/service/register-worker.service';
import { PlayingField } from '@app/shared/model/google-map';
import { AuthService } from '@app/core/service';
import { UserCredentials } from '@app/shared/model';


@Component({
  selector: 'app-assign-playing-field-and-register-dialog',
  templateUrl: './assign-playing-field-and-register-dialog.component.html',
  styleUrls: ['./assign-playing-field-and-register-dialog.component.scss']
})
export class AssignPlayingFieldAndRegisterDialogComponent  implements OnInit {


  sendError = false;
  selectedFile: File;
  hidingPassword = true;
  hidingPasswordConfirm = true;
  public registerForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<AssignPlayingFieldAndRegisterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService,
    private registerWorkerService: RegisterWorkerService) {
  }

  ngOnInit() {
    this.registerForm = new FormGroup({
      login: new FormControl(null, [Validators.required, Validators.minLength(4)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      passwordConfirm: new FormControl(null, Validators.required),
    });
  }

  processFile(event){
    this.selectedFile = event.target.files[0]
  }

  sendRequest() {
    this.registerWorkerService.sendRequestToAssignPFAndRegister(this.data.playingField, this.selectedFile,
      this.registerForm.value.login, this.registerForm.value.password).subscribe(success => {
      this.dialogRef.close();
      this.toastr.success('Prośba została wysłana, dziękujemy');
    }, error => {
      this.toastr.error('Wystąpił błąd');
    });
  }

}
