import * as tslib_1 from "tslib";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { AuthGuardService } from "./service/auth-guard.service";
import { AuthGuardWorkerService } from "./service/auth-guard-worker.service";
import { MainAuthGuardService } from './service/main-auth-guard-service';
const appRoutes = [
    {
        path: "",
        redirectTo: "search",
        pathMatch: "full"
    },
    {
        path: "search",
        loadChildren: () => import('@features/home/home.module').then(m => m.HomeModule),
        canActivate: [MainAuthGuardService]
    },
    {
        path: "admin-panel",
        loadChildren: () => import('@features/admin-panel/admin-panel.module').then(m => m.AdminPanelModule),
        canActivate: [AuthGuardService]
    },
    {
        path: "worker/playingField/:id",
        loadChildren: () => import('@features/worker/worker.module').then(m => m.WorkerModule),
        canLoad: [AuthGuardWorkerService]
    },
    {
        path: "findMatch",
        loadChildren: () => import('@features/search-match/search-match.module').then(m => m.SearchMatchModule)
    },
    {
        path: "playingField/:id",
        loadChildren: () => import('@features/playing-field/playing-field.module').then(m => m.PlayingFieldModule)
    },
    {
        path: "users",
        loadChildren: () => import('@features/user/user.module').then(m => m.UserModule)
    },
    {
        path: "search-user",
        loadChildren: () => import('@features/search-user/search-user.module').then(m => m.SearchUserModule)
    },
    {
        path: "**",
        redirectTo: "search",
        pathMatch: "full"
    }
];
let AppRoutingModule = class AppRoutingModule {
};
AppRoutingModule = tslib_1.__decorate([
    NgModule({
        imports: [RouterModule.forRoot(appRoutes, { anchorScrolling: 'enabled', scrollPositionRestoration: 'enabled' })],
        exports: [RouterModule]
    })
], AppRoutingModule);
export { AppRoutingModule };
//# sourceMappingURL=app-routing.module.js.map