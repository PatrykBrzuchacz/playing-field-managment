import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let MatchService = class MatchService {
    constructor(http) {
        this.http = http;
    }
    removeMatch(id) {
        return this.http.delete(`${API_URL}/matches/${id}`);
    }
    getPFAvailabilities(id) {
        return this.http.get(`${API_URL}/worker/playingField/${id}`);
    }
    setPFAvailability(availability, id) {
        return this.http.post(`${API_URL}/worker/playingField/${id}/setAvailability`, availability);
    }
    deletePFAvailability(id) {
        return this.http.delete(`${API_URL}/worker/playingField/playingFieldAvailabilities/${id}`);
    }
    bookPF(id, reservation) {
        return this.http.put(`${API_URL}/matches/${id}/book`, reservation);
    }
    unbookPF(id) {
        return this.http.put(`${API_URL}/matches/${id}/unbook`, null);
    }
    getMatchesByLocation(searchParams, httpParams) {
        return this.http.post(`${API_URL}/matches/getByLocation`, searchParams, { params: httpParams });
    }
    getMatchesByUserId(id, httpParams) {
        return this.http.get(`${API_URL}/matches/users/${id}/getAllMatches`, {
            params: httpParams
        });
    }
    getMatchesByUserIdAndReceiverNot(id, httpParams) {
        return this.http.get(`${API_URL}/matches/users/${id}/getAllMatchesAndReiceverNot`, {
            params: httpParams
        });
    }
    fillCode(id) {
        return this.http.put(`${API_URL}/matches/${id}/fillCode`, null);
    }
    fillCodeByCode(code) {
        let formData = new FormData();
        formData.append("code", code);
        return this.http.put(`${API_URL}/matches/fillCode`, formData);
    }
    getMatchById(id) {
        return this.http.get(`${API_URL}/matches/${id}`);
    }
};
MatchService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], MatchService);
export { MatchService };
//# sourceMappingURL=match.service.js.map