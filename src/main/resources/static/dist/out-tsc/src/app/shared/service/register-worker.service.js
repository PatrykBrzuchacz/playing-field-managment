import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
const API_URL = environment.apiUrl;
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
RegisterWorkerService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], RegisterWorkerService);
export { RegisterWorkerService };
//# sourceMappingURL=register-worker.service.js.map