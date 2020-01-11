import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
const API_URL = environment.apiUrl;
let WorkerService = class WorkerService {
    constructor(http) {
        this.http = http;
    }
    setPFAvailability(availability, id) {
        return this.http.post(`${API_URL}/worker/playingField/${id}/setAvailability`, availability);
    }
    setPFSetup(id, playingFieldSetup) {
        let formData = new FormData();
        formData.append("description", playingFieldSetup.description);
        formData.append("teamSize", playingFieldSetup.teamSize.toString());
        formData.append("pfPhoto", playingFieldSetup.pfPhoto);
        formData.append("name", playingFieldSetup.name);
        return this.http.put(`${API_URL}/worker/playingField/${id}/setup`, formData);
    }
    deletePFAvailability(id) {
        return this.http.delete(`${API_URL}/worker/playingField/playingFieldAvailabilities/${id}`);
    }
};
WorkerService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], WorkerService);
export { WorkerService };
//# sourceMappingURL=worker.service.js.map