import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let UserService = class UserService {
    constructor(http) {
        this.http = http;
    }
    getUsersForAdmin(httpParams) {
        return this.http.get(`${API_URL}/users`, { params: httpParams });
    }
    getUser(id) {
        return this.http.get(`${API_URL}/users/${id}`);
    }
    getUserUsername(id) {
        return this.http.get(`${API_URL}/users/${id}/username`);
    }
    deleteUser(userId) {
        return this.http.delete(`${API_URL}/users/${userId}`);
    }
    banUser(id) {
        return this.http.put(`${API_URL}/users/${id}/ban`, this.user);
    }
    unbanUser(id) {
        return this.http.put(`${API_URL}/users/${id}/unban`, this.user);
    }
    getLoggedUser() {
        return this.http.get(`${API_URL}/users/getLoggedUser`);
    }
    getLoggedUserWithAvatar() {
        return this.http.get(`${API_URL}/users/getLoggedUser/withAvatar`);
    }
    getUsers(city, position) {
        let formData = new FormData();
        formData.append("city", city);
        formData.append("position", position);
        return this.http.post(`${API_URL}/users`, formData);
    }
    editUser(editUser) {
        let formData = new FormData();
        formData.append("age", editUser.age ? editUser.age : '');
        formData.append("phoneNumber", editUser.phoneNumber ? editUser.phoneNumber : '');
        formData.append("avatar", editUser.avatar ? editUser.avatar : null);
        formData.append("position", editUser.position ? editUser.position : "");
        formData.append("city", editUser.city ? editUser.city : "");
        formData.append("firstName", editUser.firstName ? editUser.firstName : "");
        formData.append("lastName", editUser.lastName ? editUser.lastName : "");
        formData.append("email", editUser.email ? editUser.email : "");
        return this.http.put(`${API_URL}/users/${editUser.id}/editProfile`, formData);
    }
    getUserByUsername(username) {
        let formData = new FormData();
        formData.append("username", username);
        return this.http.post(`${API_URL}/users/byUsername`, formData);
    }
    getUsersUsername(matchId, onlyFriends) {
        return this.http.post(`${API_URL}/users/usernames/matches/${matchId}`, new OnlyFriends(onlyFriends));
    }
    deleteWorker(id) {
        return this.http.put(`${API_URL}/users/${id}/dismissWorker`, null);
    }
    restoreAccount(id) {
        return this.http.put(`${API_URL}/users/${id}/restoreAccount`, null);
    }
};
UserService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], UserService);
export { UserService };
export class OnlyFriends {
    constructor(onlyFriends) {
        this.onlyFriends = onlyFriends;
    }
}
//# sourceMappingURL=user.service.js.map