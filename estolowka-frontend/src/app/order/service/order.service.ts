import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Order} from "../model/order";
import {JsonDay} from "../model/json-day";
import {Day} from "../model/day";
import {AuthenticationService} from "../../auth/authentication.service";
import {v4 as uuid} from 'uuid';
import {Meal} from "../model/meal";

@Injectable()
export class OrderService {

  constructor(private _http: HttpClient,
              private auth: AuthenticationService) {
  }

  sendOrder(days: Day[]) {
    return this._http.post(environment.orderUrl, {
      meals: this.mapDaysToSend(days),
      userEmail: this.auth.getCurrentUserEmail()
    });
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

  getOrdersList(email: string): Observable<Order[]> {
    return this._http.get<Order[]>(environment.orderUrl + '/' + email);
  }

  convertJsonDayToDay(jsonDay: JsonDay): Day {
    var day = new Day(uuid());
    day.selectedDate = jsonDay.selectedDay;
    day.meals[0].Selected = jsonDay.breakfast;
    day.meals[1].Selected = jsonDay.dinner;
    day.meals[2].Selected = jsonDay.supper;
    return day;
  }
}
