import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let FriendsService = class FriendsService {
    constructor(http) {
        this.http = http;
    }
    getSendedRequests(httpParams) {
        return this.http.get(`${API_URL}/friends/requests/sendedRequests`, { params: httpParams });
    }
    getFriendRequests(httpParams) {
        return this.http.get(`${API_URL}/friends/requests`, {
            params: httpParams
        });
    }
    sendRequest(id) {
        return this.http.post(`${API_URL}/friends/requests/users/${id}/send`, null);
    }
    removeRequest(id) {
        return this.http.put(`${API_URL}/friends/requests/${id}/decline`, null);
    }
    acceptRequest(id) {
        return this.http.put(`${API_URL}/friends/requests/${id}/accept`, null);
    }
    getAllFriends(id) {
        return this.http.get(`${API_URL}/friends/${id}`);
    }
    deleteFriend(id) {
        return this.http.delete(`${API_URL}/friends/${id}`);
    }
    isFriend(id) {
        return this.http.get(`${API_URL}/friends/${id}/isFriend`);
    }
};
FriendsService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], FriendsService);
export { FriendsService };
//# sourceMappingURL=friends.service.js.map