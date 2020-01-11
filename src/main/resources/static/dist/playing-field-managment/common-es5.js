function _possibleConstructorReturn(self, call) { if (call && (typeof call === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["common"], {
  /***/
  "./src/app/shared/service/playing-field.service.ts":
  /*!*********************************************************!*\
    !*** ./src/app/shared/service/playing-field.service.ts ***!
    \*********************************************************/

  /*! exports provided: PlayingFieldService */

  /***/
  function srcAppSharedServicePlayingFieldServiceTs(module, __webpack_exports__, __webpack_require__) {
    "use strict";

    __webpack_require__.r(__webpack_exports__);
    /* harmony export (binding) */


    __webpack_require__.d(__webpack_exports__, "PlayingFieldService", function () {
      return PlayingFieldService;
    });
    /* harmony import */


    var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(
    /*! tslib */
    "./node_modules/tslib/tslib.es6.js");
    /* harmony import */


    var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(
    /*! @angular/core */
    "./node_modules/@angular/core/fesm2015/core.js");
    /* harmony import */


    var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(
    /*! @angular/common/http */
    "./node_modules/@angular/common/fesm2015/http.js");
    /* harmony import */


    var _env_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(
    /*! @env/environment */
    "./src/environments/environment.ts");

    var API_URL = _env_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].apiUrl;

    var PlayingFieldService =
    /*#__PURE__*/
    function () {
      function PlayingFieldService(http) {
        _classCallCheck(this, PlayingFieldService);

        this.http = http;
      }

      _createClass(PlayingFieldService, [{
        key: "deletePFAvailability",
        value: function deletePFAvailability(id) {
          return this.http.delete("".concat(API_URL, "/worker/playingField/playingFieldAvailability/").concat(id));
        }
      }, {
        key: "getPFSetup",
        value: function getPFSetup(id) {
          return this.http.get("".concat(API_URL, "/playingField/").concat(id, "/setup"));
        }
      }, {
        key: "getPFAvailabilities",
        value: function getPFAvailabilities(id, showAll) {
          return this.http.get("".concat(API_URL, "/playingField/availabilities/").concat(id), {
            params: {
              showAll: showAll
            }
          });
        }
      }, {
        key: "getMatches",
        value: function getMatches(id, searchDto, httpParams) {
          return this.http.post("".concat(API_URL, "/playingField/").concat(id, "/matches/search"), searchDto, {
            params: httpParams
          });
        }
      }]);

      return PlayingFieldService;
    }();

    PlayingFieldService.ctorParameters = function () {
      return [{
        type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]
      }];
    };

    PlayingFieldService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
      providedIn: "root"
    }), tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])], PlayingFieldService);
    /***/
  },

  /***/
  "./src/app/shared/service/register-worker.service.ts":
  /*!***********************************************************!*\
    !*** ./src/app/shared/service/register-worker.service.ts ***!
    \***********************************************************/

  /*! exports provided: RegisterWorkerService */

  /***/
  function srcAppSharedServiceRegisterWorkerServiceTs(module, __webpack_exports__, __webpack_require__) {
    "use strict";

    __webpack_require__.r(__webpack_exports__);
    /* harmony export (binding) */


    __webpack_require__.d(__webpack_exports__, "RegisterWorkerService", function () {
      return RegisterWorkerService;
    });
    /* harmony import */


    var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(
    /*! tslib */
    "./node_modules/tslib/tslib.es6.js");
    /* harmony import */


    var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(
    /*! @angular/core */
    "./node_modules/@angular/core/fesm2015/core.js");
    /* harmony import */


    var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(
    /*! @angular/common/http */
    "./node_modules/@angular/common/fesm2015/http.js");
    /* harmony import */


    var _env_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(
    /*! @env/environment */
    "./src/environments/environment.ts");

    var API_URL = _env_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].apiUrl;

    var RegisterWorkerService =
    /*#__PURE__*/
    function () {
      function RegisterWorkerService(http) {
        _classCallCheck(this, RegisterWorkerService);

        this.http = http;
      } // registerWorker(userCredentials: UserCredentials, file:File){
      //   let formData = new FormData();
      //   formData.append('file', file);
      //   formData.append('userCredentials', '{ "username": "'+  userCredentials.username +'",'+ '"password":"'+userCredentials.password + '"}')
      //   return this.http
      //     .post(`${API_URL}/workerRequests/worker/signup`, formData
      //     );
      // }


      _createClass(RegisterWorkerService, [{
        key: "getWorkerRequests",
        value: function getWorkerRequests() {
          return this.http.get("".concat(API_URL, "/workerRequests"));
        }
      }, {
        key: "acceptWorkerRequest",
        value: function acceptWorkerRequest(id) {
          var params = {
            "decision": "true"
          };
          return this.http.put("".concat(API_URL, "/workerRequests/") + id, null, {
            params: params
          });
        }
      }, {
        key: "declineWorkerRequest",
        value: function declineWorkerRequest(id) {
          var params = {
            "decision": "false"
          };
          return this.http.put("".concat(API_URL, "/workerRequests/") + id, null, {
            params: params
          });
        }
      }, {
        key: "sendRequestToAssignPF",
        value: function sendRequestToAssignPF(pf, file) {
          var formData = new FormData();
          formData.append('file', file);
          formData.append('playingField', '{ "apiId": "' + pf.apiId + '",' + '"name": "' + pf.name + '",' + '"lat":"' + pf.location.lat + '",' + '"lng":"' + pf.location.lng + '",' + '"formattedAddress":"' + pf.location.formattedAddress + '"}');
          return this.http.post("".concat(API_URL, "/assignToWorker"), formData);
        }
      }, {
        key: "sendRequestToAssignPFAndRegister",
        value: function sendRequestToAssignPFAndRegister(pf, file, login, password) {
          var formData = new FormData();
          formData.append('file', file);
          formData.append('playingField', '{ "apiId": "' + pf.apiId + '",' + '"name": "' + pf.name + '",' + '"lat":"' + pf.location.lat + '",' + '"lng":"' + pf.location.lng + '",' + '"formattedAddress":"' + pf.location.formattedAddress + '"}');
          formData.append('username', login);
          formData.append('password', password);
          return this.http.post("".concat(API_URL, "/assignToWorkerAndRegister"), formData);
        }
      }]);

      return RegisterWorkerService;
    }();

    RegisterWorkerService.ctorParameters = function () {
      return [{
        type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]
      }];
    };

    RegisterWorkerService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
      providedIn: 'root'
    }), tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])], RegisterWorkerService);
    /***/
  },

  /***/
  "./src/app/shared/utils/datePickerValidator.ts":
  /*!*****************************************************!*\
    !*** ./src/app/shared/utils/datePickerValidator.ts ***!
    \*****************************************************/

  /*! exports provided: DatePickerValidator */

  /***/
  function srcAppSharedUtilsDatePickerValidatorTs(module, __webpack_exports__, __webpack_require__) {
    "use strict";

    __webpack_require__.r(__webpack_exports__);
    /* harmony export (binding) */


    __webpack_require__.d(__webpack_exports__, "DatePickerValidator", function () {
      return DatePickerValidator;
    });
    /* harmony import */


    var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(
    /*! tslib */
    "./node_modules/tslib/tslib.es6.js");
    /* harmony import */


    var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(
    /*! @angular/forms */
    "./node_modules/@angular/forms/fesm2015/forms.js");

    var DatePickerValidator =
    /*#__PURE__*/
    function (_angular_forms__WEBPA) {
      _inherits(DatePickerValidator, _angular_forms__WEBPA);

      function DatePickerValidator() {
        _classCallCheck(this, DatePickerValidator);

        return _possibleConstructorReturn(this, _getPrototypeOf(DatePickerValidator).apply(this, arguments));
      }

      _createClass(DatePickerValidator, null, [{
        key: "fromDateValidator",
        value: function fromDateValidator(fdValue) {
          var date = fdValue.value;

          if (date === null || date === '') {
            return {
              requiredFromDate: true
            };
          }
        }
      }, {
        key: "ToDateValidator",
        value: function ToDateValidator(todValue) {
          var date = todValue.value;

          if (date === null || date === '') {
            return {
              requiredToDate: true
            };
          }
        } // Not working

      }, {
        key: "timeValidator",
        value: function timeValidator(formGroupValues) {
          console.log(formGroupValues);
          var FromDate = formGroupValues.get('FromDate').value;
          var ToDate = formGroupValues.get('ToDate').value;
          var FromTime = formGroupValues.get('FromTime').value;
          var ToTime = formGroupValues.get('ToTime').value;

          if (FromDate.toString() === ToDate.toString()) {
            var fromTime = [];
            var toTime = [];
            fromTime = FromTime.split(':');
            toTime = ToTime.split(':');

            if (parseInt(fromTime[0]) > parseInt(toTime[0])) {
              alert('condition satisfied not return any error message');
              return {
                InValidToTime: true
              };
            } else if (parseInt(fromTime[0]) === parseInt(toTime[0]) && parseInt(fromTime[1]) > parseInt(toTime[1])) {
              alert('condition satisfied not return any error message');
              return {
                InValidToTime: true
              };
            }
          }
        }
      }]);

      return DatePickerValidator;
    }(_angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"]);
    /***/

  }
}]);
//# sourceMappingURL=common-es5.js.map