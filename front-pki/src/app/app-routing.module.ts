import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserModule } from "@angular/platform-browser";
import { Routes, RouterModule } from "@angular/router";

import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import {LoginComponent} from "./pki/pki-components/login/login.component";
import {AuthGuard} from "./pki/pki-security/AuthGuard";
import {Certificate} from "./pki/pki-model-dto/backend-model/certificate";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    redirectTo: 'certificates',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        loadChildren:
          './layouts/admin-layout/admin-layout.module#AdminLayoutModule'
      }
    ],
    canActivate: [AuthGuard]
  }, {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: './layouts/auth-layout/auth-layout.module#AuthLayoutModule'
      }
    ],
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: 'certificates',
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
