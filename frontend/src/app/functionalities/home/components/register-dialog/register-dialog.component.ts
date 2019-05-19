import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { AuthService } from '@app/core/service';
import { UserCredentials, UserRole } from '@app/shared/model';
import { RegisterWorkerService } from '@app/shared/service/registerWorker.service';


@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.scss']
})

export class RegisterDialogComponent implements OnInit {

  hidingPassword = true;
  hidingPasswordConfirm = true;
  isWorker = false;
  selectedFile: File;
  public registerForm: FormGroup;

  constructor(private authService: AuthService, public dialogRef: MatDialogRef<RegisterDialogComponent>,
    private registerWorkerService: RegisterWorkerService) { }

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

  register() {
    if(this.isWorker && this.registerForm.valid){
      console.log(this.selectedFile);
      this.registerWorkerService.registerWorker(this.getRegistrationDetails(), this.selectedFile).subscribe(
        this.onSuccess, this.onFail
      )

    }
    else if (this.registerForm.valid) {
      this.authService
        .register(this.getRegistrationDetails())
        .subscribe(this.onSuccess, this.onFail);
    }
  }

  getCheckBoxvalue(event){
    this.isWorker = event.checked;
    }

    
  private getRegistrationDetails(): UserCredentials {
    const formValue = this.registerForm.value;

    return {
      username: formValue.login,
      password: formValue.password
    };
  }

  private onFail = () => {
    console.log('fail');
  }

  private onSuccess = () => {
    this.dialogRef.close();
  }
}
