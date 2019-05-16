import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderService} from '../order/service/order.service';
import {AuthenticationService} from '../auth/authentication.service';
import {MealRatingDialogComponent} from './meal-rating/dialog/meal-rating-dialog.component';
import {UserService} from '../user/service/user.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {Meal} from '../order/model/meal';
import {CustomErrorHandler} from '../util/custom-error-handler';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  @ViewChild(MealRatingDialogComponent) mealRatingDialog: MealRatingDialogComponent;
  days: any;
  userEmail = this.getUserEmail();
  isEmpty: boolean = true;
  model: any = {};
  error: string = '';
  meals: Meal[];

  constructor(private orderService: OrderService,
              private authService: AuthenticationService,
              private userService: UserService,
              private router: Router,
              private snackBar: MatSnackBar,
              private errorHandler: CustomErrorHandler) {
  }

  ngOnInit() {
  }

  getOrders(): void {
    this.orderService.getOrdersList(this.userEmail).subscribe((jsonOrders) => {
      this.meals = jsonOrders.mealsByMonth
        .reduce((meals, meal) => meals.concat(meal), []);
      console.log(this.meals);
      if (this.meals.length > 0) {
        this.isEmpty = false;
        this.meals.sort((a, b) => a.date.localeCompare(b.date));
      }
    });
  }

  getUserEmail() {
    return this.authService.getCurrentUserEmail();
  }

  reload() {
    this.getOrders();
  }

  cancelMeal(id: number) {
    this.orderService.cancelUserMeal(id).subscribe(() => {
      this.reload();
    }, (error) => {
      this.error = error.error.message;
    });
  }

  openRatingDialog(id: number) {
    this.mealRatingDialog.openDialog(id);
    this.mealRatingDialog.dialogRef.afterClosed().subscribe(() => {
      this.reload();
    });
  }

  changePassword() {
    this.userService.changeUserPassword(this.getUserEmail(), this.model.oldPassword, this.model.newPassword)
      .subscribe(() => {
        this.authService.logout();
        this.router.navigateByUrl('/login').then(() => {
          this.snackBar.open('Hasło zostało zmienione!', '', {duration: 2000});
        });
      }, (error) => {
        this.errorHandler.handleError(error);
      });
  }


  checkIfGivenDateAllowsToCancelMeal(date: string) {
    date = date + "T10:00:00";
    let deadlineDateTime: Date = new Date(date);
    deadlineDateTime.setDate(deadlineDateTime.getDate() - 1);
    let cancellationDateTime: Date = new Date();
    return deadlineDateTime > cancellationDateTime;
  }
}
