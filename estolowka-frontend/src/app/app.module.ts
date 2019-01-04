import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";
import { OrderService } from "./order/service/order.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppComponent } from './app.component';
import { CalendarComponent } from './calendar/calendar/calendar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MainPageComponent } from './main-page/main-page.component';
import { OrderFormComponent } from './order/order-form/order-form.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { OrdersComponent } from './order/orders/orders.component';
import { NgMaterialCollectionModule } from './ng-material-collection/ng-material-collection.module';
import {RouterModule, Routes} from '@angular/router';

const appRoutes: Routes = [
  { path: 'main', component: MainPageComponent },
  { path: 'order', component: OrdersComponent},
  { path: '**', redirectTo: 'main' }
];

@NgModule({
  declarations: [
    AppComponent,
    CalendarComponent,
    MainPageComponent,
    OrderFormComponent,
    OrderListComponent,
    OrdersComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    NgMaterialCollectionModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [OrderService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
