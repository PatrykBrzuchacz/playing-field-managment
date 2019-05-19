import { Component, OnInit } from '@angular/core';
import { User } from '@app/shared/model';
import { UserService } from '@app/shared/service/user.service';
import { RegisterWorkerService } from '@app/shared/service/register-worker.service';
import { WorkerRequest } from '@app/shared/model/worker-request';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-user-managment',
  templateUrl: './user-managment.component.html',
  styleUrls: ['./user-managment.component.scss']
})
export class UserManagmentComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'zbanowany', 'akcje'];
  displayedRequestColumns: string[] = ['username', 'status', 'fileName', 'proofOfWork'];
  users: User[];
  workerRequest: WorkerRequest[];
  fileUrl;
  binaryData = [];
 ImageSource;
 imageToShow: any;
image: any;
  constructor(private sanitizer: DomSanitizer,private userService: UserService, private registerWorkerService: RegisterWorkerService) { }

  ngOnInit() {
  this.getUsersForAdm();
  this.getWorkerRequests();
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
       this.imageToShow = reader.result;
    }, false);
 
    if (image) {
       reader.readAsDataURL(image);
    }
  }

  private getUsersForAdm() {
    this.userService.getUsersForAdmin().subscribe((users: any) => {
      this.users = users._embedded.users;
    });
  }

  private getWorkerRequests() {
    this.registerWorkerService.getWorkerRequests().subscribe((workers: any) => {
      this.workerRequest = workers;
      this.image= this.workerRequest[0].proofOfWork;
      // console.log(this.workerRequest[0].proofOfWork);
      // this.createImageFromBlob(this.workerRequest[0].proofOfWork);
      // this.binaryData.push(this.workerRequest[0].proofOfWork);
      // this.ImageSource = window.URL.createObjectURL(new Blob(this.binaryData, {type: "application/zip"}));
//  let mySrc;
// const reader = new FileReader();
// console.log(this.workerRequest[0].proofOfWork);
// reader.readAsDataURL(this.workerRequest[0].proofOfWork); 
// reader.onloadend = function() {
//    // result includes identifier 'data:image/png;base64,' plus the base64 data
//    mySrc = reader.result;     
// }
    })
  }

  banAccount(user: User) {
    this.userService.banUser(user.id)
    .subscribe((resp: any) => {
      this.getUsersForAdm();
    });
  }

  deleteAccount(userId: number) {
    this.userService.deleteUser(userId)
    .subscribe((resp: any) => {
      this.getUsersForAdm();
    });
  }

  unbanAccount(user: User) {
    this.userService.unbanUser(user.id)
    .subscribe((resp: any) => {
      this.getUsersForAdm();
    });
  }
}
