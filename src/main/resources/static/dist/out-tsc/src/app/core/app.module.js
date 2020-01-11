import * as tslib_1 from "tslib";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule, NO_ERRORS_SCHEMA } from "@angular/core";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app-component/app.component";
import { MatButtonModule } from "@angular/material";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterModule } from "@angular/router";
import "hammerjs";
import { AuthService, AccessTokenInterceptor } from "./service";
import { ToastrModule } from "ngx-toastr";
import { SharedModule } from "../shared/shared.module";
import { FileUploadModule } from "ng2-file-upload";
import { AuthGuardService } from "./service/auth-guard.service";
import { AuthGuardWorkerService } from "./service/auth-guard-worker.service";
import { MainAuthGuardService } from "./service/main-auth-guard-service";
let AppModule = class AppModule {
};
AppModule = tslib_1.__decorate([
    NgModule({
        declarations: [AppComponent],
        imports: [
            BrowserModule,
            BrowserAnimationsModule,
            AppRoutingModule,
            MatButtonModule,
            SharedModule,
            HttpClientModule,
            FileUploadModule,
            RouterModule,
            ToastrModule.forRoot()
        ],
        providers: [
            AuthService,
            AuthGuardService,
            AuthGuardWorkerService,
            MainAuthGuardService,
            {
                provide: HTTP_INTERCEPTORS,
                useClass: AccessTokenInterceptor,
                multi: true
            }
        ],
        bootstrap: [AppComponent],
        schemas: [NO_ERRORS_SCHEMA]
    })
], AppModule);
export { AppModule };
//# sourceMappingURL=app.module.js.map