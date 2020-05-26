import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from "./app.component";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppRoutingModule } from './app-routing.module';
import { ComponentsModule } from './components/components.module';
import { CertFormComponent } from './pki/pki-components/certificate/cert-form/cert-form.component';
import {MatRadioModule} from '@angular/material/radio';
import {MatStepperModule} from '@angular/material/stepper';
import { CertTableComponent } from './pki/pki-components/certificate/cert-table/cert-table.component';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';

import {MatInputModule} from '@angular/material/input'
import {MatSelectModule} from '@angular/material/select';
import {MatListModule} from '@angular/material/list';

import {MatTableModule} from '@angular/material/table';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { CertificateDetailsComponent } from './pki/pki-components/certificate/certificate-details/certificate-details/certificate-details.component';
import { MatTabsModule } from '@angular/material/tabs';
import { LoginComponent } from './pki/pki-components/login/login.component';
import {MatCardModule} from "@angular/material/card";
import {TokenInterceptor} from "./pki/pki-security/TokenInterceptor";


@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    MatRadioModule,
    MatStepperModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    DropDownsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatListModule,
    MatTableModule,
    MatAutocompleteModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatTabsModule,
    MatCardModule
  ],
  declarations: [AppComponent, AdminLayoutComponent, AuthLayoutComponent, CertFormComponent, CertTableComponent, CertificateDetailsComponent, LoginComponent],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {}
