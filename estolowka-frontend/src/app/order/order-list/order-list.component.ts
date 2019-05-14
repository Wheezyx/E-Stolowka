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
    this.orderService.sendOrder(this.orders).subscribe(() => {
      console.log(this.orders);
      console.log("Order added");
      this.cleanOrdersPage();
      this.openSuccessMessage();
    },
    err => {
      console.log('error occurred: ' + err.message);
      this.openErrorMessage(err);
    }
  );
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

  openErrorMessage(error: any) {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['error-msg'];
    this.snackBar.open('Wystąpił błąd ' + error.message, '', config);
  }

  cleanOrdersPage() {
    this.orders.splice(0, this.orders.length);
  }

}
