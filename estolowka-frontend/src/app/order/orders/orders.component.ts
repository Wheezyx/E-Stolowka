import { Component, OnInit } from '@angular/core';
import { Day } from '../model/day';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: Day[] = []

  constructor() {
    
  }

  ngOnInit() {
    this.addOrder()
  }

  addOrder(){
    this.orders = [...this.orders, new Day(this.orders.length + 1)]
  }
}