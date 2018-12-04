import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment";

@Injectable()
export class OrderService {
  private orders: Order;

  constructor(private _http: HttpClient) {
  }

  sendOrder(): Observable<Order> {
    return this._http.post<Order>(environment.orderUrl, this.orders);
  }
}
