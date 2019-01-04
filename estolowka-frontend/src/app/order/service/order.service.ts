import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Day } from '../model/day';
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable()
export class OrderService {

  constructor(private _http: HttpClient) {
  }

  sendOrder(orders: Day[]): Observable<Day[]> {
    return this._http.post<Day[]>(environment.orderUrl, orders);
  }
}
