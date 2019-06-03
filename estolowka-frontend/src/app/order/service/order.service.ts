import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Day} from "../model/day";
import {AuthenticationService} from "../../auth/authentication.service";
import {Meal} from "../model/meal";
import {MealsWrapper} from '../model/meals-wrapper';

@Injectable()
export class OrderService {

  constructor(private _http: HttpClient,
              private auth: AuthenticationService) {
  }

  sendOrder(days: Day[]) {
    let curr_days = days.map(x => Object.assign({}, x));
    curr_days = curr_days.map(day => this.addDay(day));
    return this._http.post(environment.orderUrl, {
      meals: this.mapDaysToSend(curr_days),
      userEmail: this.auth.getCurrentUserEmail()
    });
  }

  private addDay(day: Day) {
    let date = new Date(day.selectedDate);
    date.setDate(date.getDate() + 1);
    day.selectedDate = date.toISOString();
    return day;
  }

  private mapDaysToSend(days: Day[]) {
    var meals = [];
    days.forEach(value => {
      var date = value.selectedDate;
      value.meals.forEach(singleMeal => {
        if (singleMeal.Selected) {
          var meal = new Meal();
          meal.date = date;
          meal.type = singleMeal.type;
          meals.push(meal);
        }
      });
    });
    return meals;
  }

  getOrdersList(email: string): Observable<MealsWrapper> {
    return this._http.get<MealsWrapper>(environment.orderUrl);
  }

  rateMeal(id: number, rate: number) {
    return this._http.post(environment.orderUrl + '/' + id + '/rate', rate)
  }

  cancelUserMeal(id: number) {
    return this._http.post(environment.orderUrl + "/" + id + "/cancel", null);
  }
}
