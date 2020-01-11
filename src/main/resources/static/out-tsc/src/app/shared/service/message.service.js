import * as tslib_1 from "tslib";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "@env/environment";
const API_URL = environment.apiUrl;
let MessageService = class MessageService {
    constructor(http) {
        this.http = http;
    }
    getMessages(id) {
        return this.http.get(`${API_URL}/messages/${id}`);
    }
    sendMessage(id, content) {
        let formData = new FormData();
        formData.append("content", content);
        return this.http.post(`${API_URL}/messages/${id}/sendMessage`, formData);
    }
    getConservations(httpParams) {
        return this.http.get(`${API_URL}/messages`, {
            params: httpParams
        });
    }
};
MessageService = tslib_1.__decorate([
    Injectable({
        providedIn: "root"
    }),
    tslib_1.__metadata("design:paramtypes", [HttpClient])
], MessageService);
export { MessageService };
//# sourceMappingURL=message.service.js.map