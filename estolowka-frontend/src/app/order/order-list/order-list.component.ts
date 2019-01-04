import { Component, Input } from '@angular/core';
import { Day } from '../model/day';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent {
  @Input() orders: Day[] = []

  constructor(private orderService: OrderService) {}

  sendOrder() {
    this.orderService.sendOrder(this.orders).subscribe(() => {
      console.log("Order added");
    },
    err => {
      console.log('error occurred: ' + err.message);
    }
  );
  }
  
  deleteDay(id: number) {
    let element = this.orders.find((i)=>i.id == id);
    this.orders.splice(this.orders.indexOf(element), 1);
  }
}