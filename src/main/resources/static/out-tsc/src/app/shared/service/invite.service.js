import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let InviteService = class InviteService {
    constructor(http) {
        this.http = http;
    }
    getInvites(httpParams) {
        return this.http.get(`${API_URL}/users/invites`, {
            params: httpParams
        });
    }
    sendInvite(id, matchId) {
        return this.http.post(`${API_URL}/matches/${matchId}/invites/${id}`, null);
    }
    acceptInvite(id) {
        return this.http.put(`${API_URL}/invites/${id}/accept`, null);
    }
    declineInvite(id) {
        return this.http.put(`${API_URL}/invites/${id}/decline`, null);
    }
    getMatchInvites(id) {
        return this.http.get(`${API_URL}/matches/${id}/invites`);
    }
    getUserRequests(id) {
        return this.http.get(`${API_URL}/users/${id}/invites`);
    }
};
InviteService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], InviteService);
export { InviteService };
//# sourceMappingURL=invite.service.js.map