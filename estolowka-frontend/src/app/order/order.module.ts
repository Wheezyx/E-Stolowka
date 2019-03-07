import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderService } from './service/order.service';
import { OrderFormComponent } from './order-form/order-form.component';
import { OrderListComponent } from './order-list/order-list.component';
import { OrdersComponent } from './orders/orders.component';
import { WeeklyMenuComponent } from './weekly-menu/weekly-menu.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    OrderService
  ],
  declarations: [
    OrderFormComponent,
    OrderListComponent,
    OrdersComponent,
    WeeklyMenuComponent
  ],
  providers: [
    OrderService
  ]
})
export class OrderModule { }