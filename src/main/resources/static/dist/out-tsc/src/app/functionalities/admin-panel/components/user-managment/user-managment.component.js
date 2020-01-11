import * as tslib_1 from "tslib";
import { Component, ViewChild } from "@angular/core";
import { UserService } from "@app/shared/service/user.service";
import { RegisterWorkerService } from "@app/shared/service/register-worker.service";
import { EnlargeImageDialogComponent } from "@app/shared/components/enlarge-image-dialog/enlarge-image-dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { MatTable, MatPaginator, MatSort } from "@angular/material";
import { startWith, switchMap, map, catchError } from "rxjs/operators";
import { of, merge } from "rxjs";
let UserManagmentComponent = class UserManagmentComponent {
    constructor(userService, registerWorkerService, dialog) {
        this.userService = userService;
        this.registerWorkerService = registerWorkerService;
        this.dialog = dialog;
        this.displayedColumns = [
            "id",
            "username",
            "banned",
            "removed",
            "role",
            "action"
        ];
        this.displayedRequestColumns = [
            "id",
            "username",
            "status",
            "proofOfWork",
            "options"
        ];
    }
    ngOnInit() {
        this.handleUserTable();
        this.getWorkerRequests();
    }
    getWorkerRequests() {
        this.registerWorkerService.getWorkerRequests().subscribe(workers => {
            console.log(workers);
            workers.forEach(val => {
                if (val.status === "SENDED") {
                    val.status = "Oczekujące";
                }
            });
            this.workerRequest = workers;
        });
    }
    acceptWorkerRequest(workerRequest) {
        this.registerWorkerService
            .acceptWorkerRequest(workerRequest.id)
            .subscribe(() => {
            this.workerRequest.splice(this.workerRequest.indexOf(workerRequest), 1);
            this.workerRequestTable.renderRows();
            const user = this.users.find(val => (val.username = workerRequest.username));
            user.playingFieldId = workerRequest.pfId;
            this.usersTable.renderRows();
        });
    }
    declineWorkerRequest(workerRequest) {
        this.registerWorkerService
            .declineWorkerRequest(workerRequest.id)
            .subscribe(() => {
            this.workerRequest.splice(this.workerRequest.indexOf(workerRequest), 1);
            this.workerRequestTable.renderRows();
        });
    }
    banAccount(user) {
        this.userService.banUser(user.id).subscribe((resp) => {
            user.banned = true;
        });
    }
    deleteAccount(user) {
        this.userService.deleteUser(user.id).subscribe((resp) => {
            user.active = false;
        });
    }
    unbanAccount(user) {
        this.userService.unbanUser(user.id).subscribe((resp) => {
            user.banned = false;
        });
    }
    openImage(image) {
        this.dialog.open(EnlargeImageDialogComponent, {
            data: { image: image },
            panelClass: "custom-enlarge-dialog-container"
        });
    }
    deleteWorker(worker) {
        this.userService.deleteWorker(worker.id).subscribe(val => {
            worker.playingFieldId = null;
        });
    }
    restoreAccount(user) {
        this.userService.restoreAccount(user.id).subscribe(val => {
            user.active = true;
        });
    }
    handleUserTable() {
        this.usersPaginator.pageSize = 5;
        merge(this.sort.sortChange, this.usersPaginator.page, this.usersPaginator.pageSize)
            .pipe(startWith({}), switchMap(() => {
            const params = {
                sort: ``,
                page: this.usersPaginator.pageIndex + "",
                size: this.usersPaginator.pageSize + ""
            };
            return this.userService.getUsersForAdmin(params);
        }), map((data) => {
            this.usersLength = data.totalElements;
            return data.content;
        }), catchError(() => {
            return of([]);
        }))
            .subscribe(data => (this.users = data));
    }
};
tslib_1.__decorate([
    ViewChild("usersTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserManagmentComponent.prototype, "usersTable", void 0);
tslib_1.__decorate([
    ViewChild("workerRequestTable", { static: false }),
    tslib_1.__metadata("design:type", MatTable)
], UserManagmentComponent.prototype, "workerRequestTable", void 0);
tslib_1.__decorate([
    ViewChild(MatPaginator, { static: true }),
    tslib_1.__metadata("design:type", MatPaginator)
], UserManagmentComponent.prototype, "usersPaginator", void 0);
tslib_1.__decorate([
    ViewChild(MatSort, { static: true }),
    tslib_1.__metadata("design:type", MatSort)
], UserManagmentComponent.prototype, "sort", void 0);
UserManagmentComponent = tslib_1.__decorate([
    Component({
        selector: "app-user-managment",
        templateUrl: "./user-managment.component.html",
        styleUrls: ["./user-managment.component.scss"]
    }),
    tslib_1.__metadata("design:paramtypes", [UserService,
        RegisterWorkerService,
        MatDialog])
], UserManagmentComponent);
export { UserManagmentComponent };
//# sourceMappingURL=user-managment.component.js.map