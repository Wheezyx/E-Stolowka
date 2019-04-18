import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderService} from '../order/service/order.service';
import {JsonDay} from "../order/model/json-day";
import {Day} from '../order/model/day';
import {Order} from '../order/model/order';
import {AuthenticationService} from '../auth/authentication.service';
import {UserService} from "../user/service/user.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";
import {MealRatingDialogComponent} from './meal-rating/dialog/meal-rating-dialog.component';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  @ViewChild(MealRatingDialogComponent) mealRatingDialog: MealRatingDialogComponent;
  orders: Order[];
  days: any;
  userEmail = this.getUserEmail();
  isEmpty: boolean = true;
  model: any = {};
  error: string = '';

  constructor(private orderService: OrderService,
              private authService: AuthenticationService,
              private userService: UserService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  createConvertedOrders(jsonDays: JsonDay[]): Day[] {
    this.days = jsonDays.map((jsonDay) => this.orderService.convertJsonDayToDay(jsonDay));
    return this.days;
  }

  getOrders(): void {
    this.orderService.getOrdersList(this.userEmail).subscribe((jsonOrders) => {
      this.orders = jsonOrders;
      this.days = jsonOrders.map(order => {
        console.log(this.createConvertedOrders(order.selectedDays));
        return this.createConvertedOrders(order.selectedDays);
      });
      if (this.days.length > 0) {
        this.isEmpty = false;
      }
      console.log(this.days);
    })
  }

  getUserEmail() {
    return this.authService.getCurrentUserEmail();
  }

  changePassword() {
    this.userService.changeUserPassword(this.getUserEmail(), this.model.oldPassword, this.model.newPassword)
      .subscribe(() => {
        this.authService.logout();
        this.router.navigateByUrl('/login').then(() => {
          this.snackBar.open("Hasło zostało zmienione!", '', {duration: 2000});
        });
      }, (error) => {
        this.error = error.error.message;
      });
  }

  reload() {
    this.getOrders();
  }

  openRatingDialog() {
    this.mealRatingDialog.openDialog();
    this.mealRatingDialog.dialogRef.afterClosed().subscribe(() => {
      this.reload();
    })
  }
}
