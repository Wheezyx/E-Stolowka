import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthenticationService} from "../../auth/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class RecoverService {

  constructor(private http: HttpClient,
              private auth: AuthenticationService) { }

  recoverLink(email: string) {
    return this.http.post(environment.recoverUrl, email);
  }

  resetPassword(password: string) {
    this.http.post(environment.recoverUrl + '/reset', {password: password, token: this.auth.getTokenFromUrl()})
  }
}
