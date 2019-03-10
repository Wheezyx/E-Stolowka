import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { OrderService } from "./order/service/order.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MainPageComponent } from './main-page/main-page.component';
import { OrderFormComponent } from './order/order-form/order-form.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { OrdersComponent } from './order/orders/orders.component';
import { NgMaterialCollectionModule } from './ng-material-collection/ng-material-collection.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { AuthenticationService } from './auth/authentication.service';
import { TokenInterceptor } from './auth/token.interceptor';
import { UploadFormComponent } from './upload/upload-form/upload-form.component';
import { UploadService } from "./upload/upload.service";
import { TopNavigationComponent } from './top-navigation/top-navigation.component';
import { AdminGuard } from "./auth/admin.guard";
import { UserAccountComponent } from './user-account/user-account.component';
import { PasswordComponent } from "./login/recover/password/password.component";
import { RecoverGuard } from "./auth/recover.guard";
import { EmailDialogComponent } from "./login/recover/email-form/email-dialog/email-dialog.component";
import { EmailFormComponent } from "./login/recover/email-form/email-form.component";
import { MatFormFieldModule, MatInputModule } from "@angular/material";
import { WeeklyMenuComponent } from './order/weekly-menu/weekly-menu.component';
import { UploadMenuComponent } from './upload/upload-menu/upload-menu.component';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: MainPageComponent, canActivate: [AuthGuard]},
  { path: 'order', component: OrdersComponent, canActivate: [AuthGuard]},
  { path: 'admin', component: UploadFormComponent, canActivate: [AdminGuard]},
  { path: 'reset', component: PasswordComponent, canActivate: [RecoverGuard]},
  { path: 'account', component: UserAccountComponent, canActivate: [AuthGuard]}
];

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    OrderFormComponent,
    OrderListComponent,
    OrdersComponent,
    LoginComponent,
    UploadFormComponent,
    TopNavigationComponent,
    EmailDialogComponent,
    EmailFormComponent,
    PasswordComponent,
    UserAccountComponent,
    WeeklyMenuComponent,
    UploadMenuComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    NgMaterialCollectionModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    OrderService,
    UploadService,
    AuthGuard,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  entryComponents: [EmailFormComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
