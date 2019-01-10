import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Order} from "../model/order";
import {JsonDay} from "../model/json-day";
import {Day} from "../model/day";
import {AuthenticationService} from "../../auth/authentication.service";

@Injectable()
export class OrderService {

  constructor(private _http: HttpClient,
              private auth: AuthenticationService) {
  }

  sendOrder(days: Day[]): Observable<Day[]> {
    return this._http.post<Day[]>(environment.orderUrl, this.createOrder(days));
  }

  private convertDay(day: Day): JsonDay {
    var singleDay = new JsonDay();
    singleDay.selectedDay = day.selectedDate;
    singleDay.breakfast = day.meals[0].Selected;
    singleDay.dinner = day.meals[1].Selected;
    singleDay.supper = day.meals[2].Selected;
    return singleDay;
  }

  private createOrder(days: Day[]): Order {
    var order = new Order();
    order.selectedDays = days.map((day)=> this.convertDay(day));
    order.email = this.auth.getCurrentUserEmail();
    return order;
  }
}
