import * as tslib_1 from "tslib";
import { Component, Inject } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { DatePickerValidator } from "@app/shared/utils/datePickerValidator";
import { PFAvailabilityDto } from "@app/shared/model/availability";
import * as moment from "moment";
import { WorkerService } from "@app/shared/service/worker.service";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { ToastrService } from 'ngx-toastr';
let WorkerPFDatePickerComponent = class WorkerPFDatePickerComponent {
    constructor(data, workerService, toastrService, dialogRef) {
        this.data = data;
        this.workerService = workerService;
        this.toastrService = toastrService;
        this.dialogRef = dialogRef;
        this.minFromDate = new Date();
        this.maxToDate = new Date().setDate(2);
    }
    ngOnInit() {
        this.dateForm = new FormGroup({
            fromDate: new FormControl("", DatePickerValidator.fromDateValidator),
            toDate: new FormControl("", DatePickerValidator.ToDateValidator),
            fromTime: new FormControl(""),
            toTime: new FormControl(""),
            matchTime: new FormControl("")
        });
    }
    setPfAvailability() {
        this.availability = new PFAvailabilityDto(moment(this.dateForm.value.fromDate).format("YYYY-MM-DD"), moment(this.dateForm.value.toDate).format("YYYY-MM-DD"), this.dateForm.value.fromTime + ":00", this.dateForm.value.toTime + ":00", this.dateForm.value.matchTime);
        this.workerService
            .setPFAvailability(this.availability, this.data)
            .subscribe(val => {
            this.dialogRef.close(val);
        }, (error) => {
            if (error.status === 409) {
                this.toastrService.error("Nie można dodać rozgrywek przeszłych");
            }
            else if (error.status === 400) {
                this.toastrService.error("Daty nachodzą na siebie");
            }
            else {
                this.toastrService.error("Nie udało się dodać rozgrywek");
            }
        });
    }
};
WorkerPFDatePickerComponent = tslib_1.__decorate([
    Component({
        selector: "app-worker-pfdate-picker",
        templateUrl: "./worker-pfdate-picker.component.html",
        styleUrls: ["./worker-pfdate-picker.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, WorkerService,
        ToastrService,
        MatDialogRef])
], WorkerPFDatePickerComponent);
export { WorkerPFDatePickerComponent };
//# sourceMappingURL=worker-pfdate-picker.component.js.map