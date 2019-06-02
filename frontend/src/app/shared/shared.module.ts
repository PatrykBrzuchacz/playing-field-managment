import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatChipsModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatCheckboxModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatRadioModule,
  MatProgressSpinnerModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatSliderModule,
  MatToolbarModule,
  MatSidenavModule,
  MatAutocompleteModule,

  
} from '@angular/material';

import {HttpClientModule} from '@angular/common/http';

// import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';

import {
  faSearch,
  faWrench,
  faEnvelope
} from '@fortawesome/free-solid-svg-icons';
// Add an icon to the library for convenient access in other components
library.add(
  faSearch,
  faWrench,
  faEnvelope
);

@NgModule({
  exports: [
    CommonModule,
    // FontAwesomeModule,
    MatAutocompleteModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
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
    MatSnackBarModule
  ]
})
export class SharedModule {
}
