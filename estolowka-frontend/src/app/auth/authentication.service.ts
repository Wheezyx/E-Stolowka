import {Injectable} from '@angular/core';
import {tokenNotExpired} from 'angular2-jwt';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {SessionUser} from './sessionUser';

@Injectable()
export class AuthenticationService {

 public token: string;

 constructor(private http: HttpClient) {
 }

 getToken(): string {
   const currentUser = this.getCurrentUser();
   return currentUser && currentUser.token;
 }

 getCurrentUser(): SessionUser {
   return JSON.parse(localStorage.getItem('currentUser'));
 }

 getCurrentUserEmail(): string {
   return this.getCurrentUser().email;
 }

 isAuthenticated(): boolean {
   const token = this.getToken();
   return token && tokenNotExpired(null, token);
 }

 login(credentials, callback, errorCallback): void {
   this.getLoginResponse(credentials).subscribe(
     res => {
       let authHeader = res.headers.get('Authorization');
       if (authHeader) {
         this.token = authHeader;
         localStorage.setItem('currentUser',
           JSON.stringify(<SessionUser>{email: credentials.email, token: this.token}));
       }
       return callback && callback();
     }, error => {
       return errorCallback && errorCallback(error);
     });
 }

 getLoginResponse(credentials): Observable<HttpResponse<any>> {
   var data = {email: credentials.email, password: credentials.password};
   return this.http.post(environment.loginUrl, data, {observe: 'response'});
 }

 logout(): void {
   this.token = null;
   localStorage.removeItem('currentUser');
 }
}

export interface JWTPayload {
 exp: number;
 iss: string;
 sub: string;
}