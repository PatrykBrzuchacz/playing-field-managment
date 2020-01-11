import * as tslib_1 from "tslib";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
let GoogleService = class GoogleService {
    constructor(http) {
        this.http = http;
    }
    getGooglePlaces(lat, lng) {
        const params = new HttpParams()
            .set('lat', `${lat}`)
            .set('lng', `${lng}`);
        return this.http.get('/api/searchByLocation', { params: params });
    }
};
GoogleService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], GoogleService);
export { GoogleService };
//# sourceMappingURL=google.service.js.map