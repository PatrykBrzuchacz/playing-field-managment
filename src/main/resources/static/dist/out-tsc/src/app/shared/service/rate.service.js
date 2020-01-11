import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let RateService = class RateService {
    constructor(http) {
        this.http = http;
    }
    getRate(id) {
        return this.http.get(`${API_URL}/playingField/${id}/getRate`);
    }
    addRate(id, rate) {
        let formData = new FormData();
        formData.append("rate", rate);
        return this.http.post(`${API_URL}/playingField/${id}/addVote`, formData);
    }
    existsRateByLoggedUser(id) {
        return this.http.get(`${API_URL}/playingField/${id}/existsRate`);
    }
};
RateService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], RateService);
export { RateService };
//# sourceMappingURL=rate.service.js.map