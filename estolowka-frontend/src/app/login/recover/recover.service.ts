import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {RecoverGuard} from "../../auth/recover.guard";

@Injectable({
  providedIn: 'root'
})
export class RecoverService {

  constructor(private http: HttpClient,
              private guard: RecoverGuard) {
  }

  recoverLink(email: string) {
    return this.http.post(environment.recoverUrl, email);
  }

  resetPassword(password: string) {
    this.http.post(environment.recoverUrl + '/reset', {password: password, token: this.guard.getToken()}).subscribe();
  }
}
