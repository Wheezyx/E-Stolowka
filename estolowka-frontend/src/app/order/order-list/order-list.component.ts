import { Component, Input } from '@angular/core';
import { Day } from '../model/day';
import { OrderService } from '../service/order.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { CustomErrorHandler } from '../../util/custom-error-handler';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent {
  @Input() orders: Day[] = []

  constructor(private orderService: OrderService,
              private snackBar: MatSnackBar,
              private errorHandler: CustomErrorHandler) {}

  sendOrder() {
    this.orders = this.orders.map(day => this.addDay(day));
    this.orderService.sendOrder(this.orders).subscribe(() => {
      console.log(this.orders);
      console.log("Order added");
      this.cleanOrdersPage();
      this.openSuccessMessage();
    },
    err => {
      console.log('error occurred: ' + err.message);
      this.errorHandler.handleError(err);
    }
  );
  }

  addDay(day: Day){
    let date = new Date(day.selectedDate);
    date.setDate(date.getDate()+1);
    day.selectedDate = date.toISOString();
    return day;
  }

  isExist(date: Date): boolean {
    return this.orders.some(day => day.selectedDate == date.toString());
  }

  deleteDay(id: number) {
    let element = this.orders.find((i)=>i.id == id);
    this.orders.splice(this.orders.indexOf(element), 1);
    let indexOfNextElementAfterDeletedOne = this.orders.indexOf(element) + 1;
    for (let i = indexOfNextElementAfterDeletedOne; i < this.orders.length; i++) {
      this.orders[i].id = i+1;
    }
  }

  openSuccessMessage() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['success-msg'];
    this.snackBar.open('Zamówienie zostało wysłane!', '', config);
  }

  cleanOrdersPage() {
    this.orders.splice(0, this.orders.length);
  }

}
