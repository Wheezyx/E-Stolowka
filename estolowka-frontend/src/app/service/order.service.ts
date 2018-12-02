import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";

@Injectable()
export class OrderService {
  private orders: Order;

  constructor(private _http: HttpClient) {
  }

  sendOrder(): Observable<Order> {
    return this._http.post<Order>(environment.baseUrl, this.orders);
  }
}
