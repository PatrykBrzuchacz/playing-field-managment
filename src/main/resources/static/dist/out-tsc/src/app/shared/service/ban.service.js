import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let BanService = class BanService {
    constructor(http) {
        this.http = http;
    }
    getBans(playingFieldId) {
        return this.http.get(`${API_URL}/playingField/${playingFieldId}/bans`);
    }
    banUser(playingFieldId, userId) {
        return this.http.post(`${API_URL}/playingField/${playingFieldId}/users/${userId}/ban`, null);
    }
    unbanUser(playingFieldId, userId) {
        return this.http.put(`${API_URL}/playingField/${playingFieldId}/users/${userId}/unban`, null);
    }
    isUserBanned(userId, playingFieldId) {
        return this.http.get(`${API_URL}/playingField/${playingFieldId}/users/${userId}/isBanned`);
    }
};
BanService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], BanService);
export { BanService };
//# sourceMappingURL=ban.service.js.map