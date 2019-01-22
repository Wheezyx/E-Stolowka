import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order/service/order.service';
import { JsonDay } from "../order/model/json-day";
import { Day } from '../order/model/day';
import { Order } from '../order/model/order';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {
  orders: Order[];
  days: any;

  constructor(private orderService: OrderService) { }

  ngOnInit() {
  }

  createConvertedOrders(jsonDays: JsonDay[]): Day[] {
    this.days = jsonDays.map((jsonDay) => this.orderService.convertJsonDayToDay(jsonDay));
    return this.days;
  }

  getOrders(): void {
    this.orderService.getOrdersList("user@gmail.com").subscribe((jsonOrders) => {
      this.orders = jsonOrders;
      //array of day arrays
      this.days = jsonOrders.map(order => {
        console.log(this.createConvertedOrders(order.selectedDays));
        return this.createConvertedOrders(order.selectedDays);
      });
      console.log(this.days);
    })
  }
  
}