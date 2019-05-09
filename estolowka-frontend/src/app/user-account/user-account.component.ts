import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderService} from '../order/service/order.service';
import {AuthenticationService} from '../auth/authentication.service';
import {MealRatingDialogComponent} from './meal-rating/dialog/meal-rating-dialog.component';
import {UserService} from '../user/service/user.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {Meal} from '../order/model/meal';

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
              private snackBar: MatSnackBar) {
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

  openRatingDialog() {
    this.mealRatingDialog.openDialog();
    this.mealRatingDialog.dialogRef.afterClosed().subscribe(() => {
      this.reload();
    })
  }

  changePassword() {
    this.userService.changeUserPassword(this.getUserEmail(), this.model.oldPassword, this.model.newPassword)
      .subscribe(() => {
        this.authService.logout();
        this.router.navigateByUrl('/login').then(() => {
          this.snackBar.open('Hasło zostało zmienione!', '', {duration: 2000});
        });
      }, (error) => {
        this.error = error.error.message;
      });
  }
}
