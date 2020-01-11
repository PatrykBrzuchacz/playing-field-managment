import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
const API_URL = environment.apiUrl;
let TeamService = class TeamService {
    constructor(http) {
        this.http = http;
    }
    getTeamsByMatch(id) {
        return this.http.get(`${API_URL}/match/${id}/teams`);
    }
    joinToTeam(id) {
        return this.http.post(`${API_URL}/team/${id}/join`, null);
    }
    exitTeam(id) {
        return this.http.delete(`${API_URL}/team/${id}/exit`);
    }
    swapTeam(id) {
        return this.http.put(`${API_URL}/match/${id}/swapTeam`, null);
    }
};
TeamService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], TeamService);
export { TeamService };
//# sourceMappingURL=team.service.js.map