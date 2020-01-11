import * as tslib_1 from "tslib";
import { RouterModule } from "@angular/router";
import { NgModule, LOCALE_ID } from "@angular/core";
import { CommonModule, registerLocaleData } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { AmazingTimePickerModule } from "amazing-time-picker";
import { NgxMaterialTimepickerModule } from "ngx-material-timepicker";
import localePl from "@angular/common/locales/pl";
import { AngularEditorModule } from "@kolkov/angular-editor";
registerLocaleData(localePl);
export const DATE_FORMAT = {
    parse: {
        dateInput: "LL"
    },
    display: {
        dateInput: "YYYY-MM-DD",
        monthYearLabel: "YYYY",
        dateA11yLabel: "LL",
        monthYearA11yLabel: "YYYY"
    }
};
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from "@angular/material/core";
import { AngularSvgIconModule } from "angular-svg-icon";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatButtonModule } from "@angular/material/button";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { MatCardModule } from "@angular/material/card";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatChipsModule } from "@angular/material/chips";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { 
// FontAwesomeModule,
MatSelectModule } from "@angular/material/select";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSliderModule } from "@angular/material/slider";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatTooltipModule } from "@angular/material/tooltip";
import { HttpClientModule } from "@angular/common/http";
// import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from "@fortawesome/fontawesome-svg-core";
import { faSearch, faWrench, faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { LoginDialogComponent } from "./components/security/login-dialog/login-dialog.component";
import { RegisterDialogComponent } from "./components/security/register-dialog/register-dialog.component";
import { SidebarComponent } from "./components/sidebar/sidebar.component";
import { HeaderComponent } from "./components/header/header.component";
import { DragDropDirective } from "./directive/drag-and-drop-directive";
import { EnlargeImageDialogComponent } from "./components/enlarge-image-dialog/enlarge-image-dialog.component";
import { TeamsDialogComponent } from "@app/functionalities/playing-field/components/teams-dialog/teams-dialog.component";
import { ReservationDialogComponent } from "@app/functionalities/playing-field/components/reservation-dialog/reservation-dialog.component";
import { MessageDialogComponent } from "./components/message-dialog/message-dialog.component";
import { ChooseMatchDialogComponent } from "./components/choose-match-dialog/choose-match-dialog.component";
import { WorkerBanDialogComponent } from "./components/worker-ban-dialog/worker-ban-dialog.component";
import { CodeDialogComponent } from "@app/functionalities/playing-field/components/code-dialog/code-dialog.component";
import { NotificationsComponent } from './components/notifications/notifications.component';
// Add an icon to the library for convenient access in other components
library.add(faSearch, faWrench, faEnvelope);
let SharedModule = class SharedModule {
};
SharedModule = tslib_1.__decorate([
    NgModule({
        declarations: [
            LoginDialogComponent,
            EnlargeImageDialogComponent,
            RegisterDialogComponent,
            SidebarComponent,
            HeaderComponent,
            DragDropDirective,
            ReservationDialogComponent,
            TeamsDialogComponent,
            MessageDialogComponent,
            ChooseMatchDialogComponent,
            WorkerBanDialogComponent,
            CodeDialogComponent,
            NotificationsComponent
        ],
        imports: [
            NgxMaterialTimepickerModule,
            CommonModule,
            MatSelectModule,
            // FontAwesomeModule,
            AngularEditorModule,
            MatAutocompleteModule,
            MatIconModule,
            FormsModule,
            ReactiveFormsModule,
            MatMenuModule,
            MatButtonModule,
            MatDialogModule,
            MatFormFieldModule,
            AngularSvgIconModule,
            MatInputModule,
            ReactiveFormsModule,
            MatCheckboxModule,
            MatRadioModule,
            MatIconModule,
            MatSortModule,
            MatPaginatorModule,
            MatProgressSpinnerModule,
            MatInputModule,
            MatGridListModule,
            MatCardModule,
            MatTabsModule,
            MatChipsModule,
            MatDividerModule,
            MatListModule,
            MatTableModule,
            RouterModule,
            MatSidenavModule,
            HttpClientModule,
            MatTableModule,
            MatSliderModule,
            MatToolbarModule,
            MatExpansionModule,
            MatButtonToggleModule,
            MatSnackBarModule,
            MatDatepickerModule,
            MatNativeDateModule,
            AmazingTimePickerModule,
            MatTooltipModule,
            CommonModule
        ],
        exports: [
            HeaderComponent,
            EnlargeImageDialogComponent,
            CommonModule,
            SidebarComponent,
            AngularSvgIconModule,
            NgxMaterialTimepickerModule,
            AmazingTimePickerModule,
            MatSelectModule,
            MatAutocompleteModule,
            MatIconModule,
            FormsModule,
            ReactiveFormsModule,
            MatMenuModule,
            MatButtonModule,
            MatDialogModule,
            MatFormFieldModule,
            MatInputModule,
            ReactiveFormsModule,
            MatCheckboxModule,
            MatRadioModule,
            MatIconModule,
            MatSortModule,
            MatPaginatorModule,
            MatTooltipModule,
            MatProgressSpinnerModule,
            MatInputModule,
            MatGridListModule,
            MatCardModule,
            MatTabsModule,
            MatChipsModule,
            MatDividerModule,
            MatListModule,
            MatTableModule,
            RouterModule,
            MatSidenavModule,
            HttpClientModule,
            MatTableModule,
            MatSliderModule,
            MatToolbarModule,
            MatExpansionModule,
            MatButtonToggleModule,
            MatSnackBarModule,
            MatDatepickerModule,
            MatNativeDateModule,
            RouterModule,
            DragDropDirective,
            AngularEditorModule
        ],
        entryComponents: [
            LoginDialogComponent,
            RegisterDialogComponent,
            EnlargeImageDialogComponent,
            ReservationDialogComponent,
            TeamsDialogComponent,
            MessageDialogComponent,
            ChooseMatchDialogComponent,
            WorkerBanDialogComponent,
            CodeDialogComponent
        ],
        providers: [
            { provide: MAT_DATE_FORMATS, useValue: DATE_FORMAT },
            {
                provide: DateAdapter,
                useClass: MomentDateAdapter,
                deps: [MAT_DATE_LOCALE]
            },
            // { provide: MAT_DATE_FORMATS, useValue: DATE_FORMAT },
            { provide: LOCALE_ID, useValue: "pl" }
        ]
    })
], SharedModule);
export { SharedModule };
//# sourceMappingURL=shared.module.js.map