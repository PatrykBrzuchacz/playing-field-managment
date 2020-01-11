import { Validators } from '@angular/forms';
export class DatePickerValidator extends Validators {
    static fromDateValidator(fdValue) {
        const date = fdValue.value;
        if (date === null || date === '') {
            return { requiredFromDate: true };
        }
    }
    static ToDateValidator(todValue) {
        const date = todValue.value;
        if (date === null || date === '') {
            return { requiredToDate: true };
        }
    }
    // Not working
    static timeValidator(formGroupValues) {
        console.log(formGroupValues);
        const FromDate = formGroupValues.get('FromDate').value;
        const ToDate = formGroupValues.get('ToDate').value;
        const FromTime = formGroupValues.get('FromTime').value;
        const ToTime = formGroupValues.get('ToTime').value;
        if (FromDate.toString() === ToDate.toString()) {
            let fromTime = [];
            let toTime = [];
            fromTime = FromTime.split(':');
            toTime = ToTime.split(':');
            if (parseInt(fromTime[0]) > parseInt(toTime[0])) {
                alert('condition satisfied not return any error message');
                return { InValidToTime: true };
            }
            else if (parseInt(fromTime[0]) === parseInt(toTime[0]) &&
                parseInt(fromTime[1]) > parseInt(toTime[1])) {
                alert('condition satisfied not return any error message');
                return { InValidToTime: true };
            }
        }
    }
}
//# sourceMappingURL=datePickerValidator.js.map