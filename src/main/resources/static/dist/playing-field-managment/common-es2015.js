(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["common"],{

/***/ "./src/app/shared/service/playing-field.service.ts":
/*!*********************************************************!*\
  !*** ./src/app/shared/service/playing-field.service.ts ***!
  \*********************************************************/
/*! exports provided: PlayingFieldService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PlayingFieldService", function() { return PlayingFieldService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _env_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @env/environment */ "./src/environments/environment.ts");




const API_URL = _env_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].apiUrl;
let PlayingFieldService = class PlayingFieldService {
    constructor(http) {
        this.http = http;
    }
    deletePFAvailability(id) {
        return this.http.delete(`${API_URL}/worker/playingField/playingFieldAvailability/${id}`);
    }
    getPFSetup(id) {
        return this.http.get(`${API_URL}/playingField/${id}/setup`);
    }
    getPFAvailabilities(id, showAll) {
        return this.http.get(`${API_URL}/playingField/availabilities/${id}`, { params: { showAll: showAll } });
    }
    getMatches(id, searchDto, httpParams) {
        return this.http.post(`${API_URL}/playingField/${id}/matches/search`, searchDto, { params: httpParams });
    }
};
PlayingFieldService.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] }
];
PlayingFieldService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
        providedIn: "root"
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
], PlayingFieldService);



/***/ }),

/***/ "./src/app/shared/service/register-worker.service.ts":
/*!***********************************************************!*\
  !*** ./src/app/shared/service/register-worker.service.ts ***!
  \***********************************************************/
/*! exports provided: RegisterWorkerService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RegisterWorkerService", function() { return RegisterWorkerService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _env_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @env/environment */ "./src/environments/environment.ts");




const API_URL = _env_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].apiUrl;
let RegisterWorkerService = class RegisterWorkerService {
    constructor(http) {
        this.http = http;
    }
    // registerWorker(userCredentials: UserCredentials, file:File){
    //   let formData = new FormData();
    //   formData.append('file', file);
    //   formData.append('userCredentials', '{ "username": "'+  userCredentials.username +'",'+ '"password":"'+userCredentials.password + '"}')
    //   return this.http
    //     .post(`${API_URL}/workerRequests/worker/signup`, formData
    //     );
    // }
    getWorkerRequests() {
        return this.http.get(`${API_URL}/workerRequests`);
    }
    acceptWorkerRequest(id) {
        const params = { "decision": "true" };
        return this.http.put(`${API_URL}/workerRequests/` + id, null, { params });
    }
    declineWorkerRequest(id) {
        const params = { "decision": "false" };
        return this.http.put(`${API_URL}/workerRequests/` + id, null, { params });
    }
    sendRequestToAssignPF(pf, file) {
        let formData = new FormData();
        formData.append('file', file);
        formData.append('playingField', '{ "apiId": "' + pf.apiId + '",' + '"name": "' + pf.name + '",' + '"lat":"' + pf.location.lat + '",' +
            '"lng":"' + pf.location.lng + '",' + '"formattedAddress":"' + pf.location.formattedAddress + '"}');
        return this.http.post(`${API_URL}/assignToWorker`, formData);
    }
    sendRequestToAssignPFAndRegister(pf, file, login, password) {
        let formData = new FormData();
        formData.append('file', file);
        formData.append('playingField', '{ "apiId": "' + pf.apiId + '",' + '"name": "' + pf.name + '",' + '"lat":"' + pf.location.lat + '",' +
            '"lng":"' + pf.location.lng + '",' + '"formattedAddress":"' + pf.location.formattedAddress + '"}');
        formData.append('username', login);
        formData.append('password', password);
        return this.http.post(`${API_URL}/assignToWorkerAndRegister`, formData);
    }
};
RegisterWorkerService.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] }
];
RegisterWorkerService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
        providedIn: 'root'
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
], RegisterWorkerService);



/***/ }),

/***/ "./src/app/shared/utils/datePickerValidator.ts":
/*!*****************************************************!*\
  !*** ./src/app/shared/utils/datePickerValidator.ts ***!
  \*****************************************************/
/*! exports provided: DatePickerValidator */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DatePickerValidator", function() { return DatePickerValidator; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");


class DatePickerValidator extends _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"] {
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


/***/ })

}]);
//# sourceMappingURL=common-es2015.js.map