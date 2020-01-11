import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
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
PlayingFieldService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], PlayingFieldService);
export { PlayingFieldService };
//# sourceMappingURL=playing-field.service.js.map