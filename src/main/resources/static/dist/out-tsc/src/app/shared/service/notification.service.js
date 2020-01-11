import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let NotificationService = class NotificationService {
    constructor(http) {
        this.http = http;
    }
    getNotifications() {
        return this.http.get(`${API_URL}/notifications`);
    }
    setAsDisplayed(id, accepted) {
        let formData = new FormData();
        formData.append("accepted", accepted);
        return this.http.put(`${API_URL}/notifications/${id}/setAsDisplayed`, formData);
    }
    setAllAsDisplayed() {
        return this.http.put(`${API_URL}/notifications/setAllAsDisplayed`, null);
    }
};
NotificationService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], NotificationService);
export { NotificationService };
//# sourceMappingURL=notification.service.js.map