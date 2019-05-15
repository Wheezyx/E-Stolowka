import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
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
import { MatButtonModule, MatFormFieldModule, MatInputModule, MatSnackBarModule } from "@angular/material";
import { WeeklyMenuComponent } from './order/weekly-menu/weekly-menu.component';
import { UploadMenuComponent } from './menu/upload-menu/upload-menu.component';
import { UploadMenuFormComponent } from './menu/upload-menu-form/upload-menu-form.component';
import { MenuService } from './menu/menu.service';
import { InformationPageComponent } from './information-page/information-page.component';
import { UploadMenuPricesFormComponent } from './menu/upload-menu-prices-form/upload-menu-prices-form.component';
import { UserComponent } from './user/user.component';
import { UserService } from './user/service/user.service';
import { AdminComponent } from './admin/admin.component';
import { MealRatingComponent } from './user-account/meal-rating/meal-rating.component';
import { MealRatingDialogComponent } from './user-account/meal-rating/dialog/meal-rating-dialog.component';
import { StarRatingModule } from 'angular-star-rating';
import { CustomErrorHandler } from './util/custom-error-handler';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent },
  {path: '', component: MainPageComponent, canActivate: [AuthGuard]},
  {path: 'order', component: OrdersComponent, canActivate: [AuthGuard]},
  {path: 'reset', component: PasswordComponent, canActivate: [RecoverGuard]},
  {path: 'account', component: UserAccountComponent, canActivate: [AuthGuard]},
  {path: 'information', component: InformationPageComponent, canActivate: [AuthGuard]},
  {path: 'account', component: UserAccountComponent, canActivate: [AuthGuard]},
  {path: 'admin', component: AdminComponent, canActivate: [AdminGuard]},
  {path: 'admin/price', component: UploadMenuPricesFormComponent, canActivate: [AdminGuard]},
  {path: 'admin/users', component: UserComponent, canActivate: [AdminGuard]},
  {path: 'admin/usersUpload', component: UploadFormComponent, canActivate: [AdminGuard]},
  {path: 'admin/menu', component: UploadMenuComponent, canActivate: [AdminGuard]}
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
    UploadMenuComponent,
    UploadMenuFormComponent,
    InformationPageComponent,
    UploadMenuPricesFormComponent,
    UserComponent,
    AdminComponent,
    MealRatingComponent,
    MealRatingDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatSnackBarModule,
    MatInputModule,
    NgMaterialCollectionModule,
    RouterModule.forRoot(appRoutes),
    StarRatingModule.forRoot()
  ],
  providers: [
    OrderService,
    UploadService,
    MenuService,
    UserService,
    AuthGuard,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    CustomErrorHandler,
    {
      provide: ErrorHandler,
      useClass: CustomErrorHandler,
    },
  ],
  entryComponents: [
    EmailFormComponent,
    MealRatingComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
