import { Component, OnInit } from '@angular/core';
import { User } from '@app/shared/model';
import { UserService } from '@app/shared/service/user.service';
import { RegisterWorkerService } from '@app/shared/service/register-worker.service';
import { WorkerRequest } from '@app/shared/model/worker-request';
import { DomSanitizer } from '@angular/platform-browser';
import { DialogService } from '@app/shared/service/dialog.service';

@Component({
  selector: 'app-user-managment',
  templateUrl: './user-managment.component.html',
  styleUrls: ['./user-managment.component.scss']
})
export class UserManagmentComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'zbanowany', 'akcje'];
  displayedRequestColumns: string[] = ['username', 'status', 'fileName', 'PFApiId', 'proofOfWork','options'];
  users: User[];
  workerRequest: WorkerRequest[];
  constructor(private sanitizer: DomSanitizer,
    private userService: UserService,
    private registerWorkerService: RegisterWorkerService,
    private dialogService: DialogService) { }

  ngOnInit() {
  this.getUsersForAdm();
  this.getWorkerRequests();
  }



  private getUsersForAdm() {
    this.userService.getUsersForAdmin().subscribe((users: any) => {
      this.users = users._embedded.users;
    });
  }

  private getWorkerRequests() {
    this.registerWorkerService.getWorkerRequests().subscribe((workers: any) => {
      this.workerRequest = workers;
    })
  }

   acceptWorkerRequest(id: number) {
    this.registerWorkerService.acceptWorkerRequest(id).subscribe(() => {
      this.getWorkerRequests();
    })
  }

   declineWorkerRequest(id: number) {
    this.registerWorkerService.declineWorkerRequest(id).subscribe(() => {
      this.getWorkerRequests();
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
  openImage(image: File) {
    this.dialogService.openEnlargeImageDialog(image);
  }
}
