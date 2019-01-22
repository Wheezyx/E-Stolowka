import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order/service/order.service';
import {Day} from "../order/model/day";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {
  orders: Day[];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
  }

  getOrders(): void {
    this.orderService.getOrdersList("admin@admin.pl").subscribe((orders) => {
      this.orders = orders;
    })
  }
  
}
