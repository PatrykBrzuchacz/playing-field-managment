import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { AUTHORIZATION_HEADER, AuthService } from './auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs/internal/observable/throwError';
let AccessTokenInterceptor = class AccessTokenInterceptor {
    constructor(authService, toastr) {
        this.authService = authService;
        this.toastr = toastr;
        this.handleExpiredTokenError = () => {
            this.toastr.error('Twoja sesja wygasła, zaloguj się ponownie', 'Błąd');
            this.authService.logout();
            return throwError('session expired');
        };
        this.jwtHelper = new JwtHelperService();
    }
    intercept(request, next) {
        const authHeader = AUTHORIZATION_HEADER;
        const accessToken = this.authService.getAuthorization();
        if (accessToken !== null) {
            if (this.jwtHelper.isTokenExpired(accessToken)) {
                return this.handleExpiredTokenError();
            }
            request = request.clone({
                headers: request.headers.set(authHeader, accessToken),
                withCredentials: false
            });
        }
        return next.handle(request);
    }
};
AccessTokenInterceptor = tslib_1.__decorate([
    Injectable(),
    tslib_1.__metadata("design:paramtypes", [AuthService, ToastrService])
], AccessTokenInterceptor);
export { AccessTokenInterceptor };
//# sourceMappingURL=access-token-interceptor.js.map