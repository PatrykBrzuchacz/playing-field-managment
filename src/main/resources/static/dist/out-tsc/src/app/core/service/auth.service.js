import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { tap } from "rxjs/operators";
import { environment } from "@env/environment";
import { Role } from "@app/shared/model";
import { UserService } from "@app/shared/service/user.service";
const API_URL = environment.apiUrl;
export const AUTHORIZATION_HEADER = "Authorization";
const AUTHORIZATION_KEY = "authorization";
const USERNAME_KEY = "username";
const ROLE_KEY = "role";
let AuthService = class AuthService {
    constructor(http, userService) {
        this.http = http;
        this.userService = userService;
    }
    login(userCredentials) {
        return this.http
            .post(`${API_URL}/login`, userCredentials, {
            observe: "response"
        })
            .pipe(tap((response) => {
            const token = response.headers.get(AUTHORIZATION_HEADER);
            const body = response.body;
            this.storeAccountInfo(body);
            this.storeAuthorization(token);
        }));
    }
    getAuthorization() {
        return localStorage.getItem(AUTHORIZATION_KEY);
    }
    storeAuthorization(authToken) {
        localStorage.setItem(AUTHORIZATION_KEY, authToken);
    }
    storeAccountInfo(accountInfo) {
        localStorage.setItem(USERNAME_KEY, accountInfo.username);
        localStorage.setItem(ROLE_KEY, accountInfo.role.toString());
    }
    logout() {
        const pfId = localStorage.getItem("playingFieldId");
        if (pfId) {
            this.checkWarnings(pfId).subscribe(() => {
            }, () => { }, () => {
                window.location.reload();
            });
        }
        else {
            window.location.reload();
        }
        localStorage.removeItem(AUTHORIZATION_KEY);
        localStorage.removeItem(USERNAME_KEY);
        localStorage.removeItem(ROLE_KEY);
        localStorage.removeItem("userId");
        localStorage.removeItem("playingFieldId");
    }
    checkWarnings(pfId) {
        return this.http.get(`${API_URL}/playingField/${pfId}/setWarnings`);
    }
    isLogged() {
        return localStorage.getItem(AUTHORIZATION_KEY) !== null;
    }
    isAdmin() {
        return this.hasRole(Role.ADMIN);
    }
    isUser() {
        return this.hasRole(Role.USER);
    }
    isWorker() {
        return this.hasRole(Role.WORKER);
    }
    hasRole(role) {
        return localStorage.getItem("role") === role.toString();
    }
    register(registerUserDto) {
        const url = `${API_URL}/users/signup`;
        return this.http.post(url, registerUserDto).pipe(tap(response => {
            this.login(registerUserDto.userCredentials);
        }));
    }
};
AuthService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient, UserService])
], AuthService);
export { AuthService };
//# sourceMappingURL=auth.service.js.map