import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, BehaviorSubject } from 'rxjs';
let DataSharingService = class DataSharingService {
    constructor(router) {
        this.router = router;
        this._stack = [];
        //todo refactor
        this.isClosed = new Subject();
        this.getUser = new BehaviorSubject('default');
        this.currentUser = this.getUser.asObservable();
        this.name = new BehaviorSubject('');
    }
    changeUser(message) {
        this.getUser.next(message);
    }
    push(item) {
        this._stack.push(item);
    }
    pop() {
        if (this._stack.length) {
            return this._stack.pop();
        }
        return undefined;
    }
    getStack() {
        return this._stack;
    }
    clearStack() {
        this._stack = [];
    }
    isStackEmpty() {
        return this._stack.length === 0;
    }
    shouldStackBeCleared(destinationUrl) {
        return (!this.isStackEmpty() &&
            !(this.shouldStateBeRestored(destinationUrl) ||
                this.areUrlsEqual(this.getLast().destinationUrl, destinationUrl)));
    }
    shouldStateBeRestored(currentUrl) {
        return (!this.isStackEmpty() &&
            this.areUrlsEqual(this.getLast().returnUrl, currentUrl));
    }
    updateStackAndProceed(outValue, outType) {
        if (!this.isStackEmpty()) {
            this.getLast().out = {
                value: outValue,
                type: outType
            };
            this.proceed();
        }
    }
    proceed() {
        if (!this.isStackEmpty()) {
            this.router.navigate([this.getLast().returnUrl]);
        }
    }
    getLast() {
        return this._stack[this._stack.length - 1];
    }
    areUrlsEqual(url1, url2) {
        return this.removeQueryParams(url1) === this.removeQueryParams(url2);
    }
    removeQueryParams(url) {
        if (url && url.indexOf('?') >= 0) {
            return url.substring(0, url.indexOf('?'));
        }
        else {
            return url;
        }
    }
};
DataSharingService = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    }),
    tslib_1.__metadata("design:paramtypes", [Router])
], DataSharingService);
export { DataSharingService };
//# sourceMappingURL=data-sharing.service.js.map