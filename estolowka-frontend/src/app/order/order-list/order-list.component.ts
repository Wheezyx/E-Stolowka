import { Component, Input } from '@angular/core';
import { Day } from '../model/day';
import { OrderService } from '../service/order.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent {
  @Input() orders: Day[] = []

  constructor(private orderService: OrderService,
              private snackBar: MatSnackBar) {}

  sendOrder() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;

    this.orderService.sendOrder(this.orders).subscribe(() => {
      console.log("Order added");
      this.refreshPage();
      config.panelClass = ['success-msg'];
      this.snackBar.open('Zamówienie zostało wysłane!', '', config);
    },
    err => {
      console.log('error occurred: ' + err.message);
      config.panelClass = ['error-msg'];
      this.snackBar.open('Wystąpił błąd ' + err.message, '', config);
    }
  );
  }
  
  deleteDay(id: number) {
    let element = this.orders.find((i)=>i.id == id);
    this.orders.splice(this.orders.indexOf(element), 1);
    let indexOfNextElementAfterDeletedOne = this.orders.indexOf(element) + 1;
    for (let i = indexOfNextElementAfterDeletedOne; i < this.orders.length; i++) {
      this.orders[i].id = i+1;
    } 
  }

  refreshPage() {
    location.reload();
  }
  
}