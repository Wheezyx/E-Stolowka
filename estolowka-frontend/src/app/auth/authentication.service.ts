import {Injectable} from '@angular/core';
import {tokenNotExpired} from 'angular2-jwt';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {SessionUser} from './sessionUser';
import {ActivatedRoute} from "@angular/router";

@Injectable()
export class AuthenticationService {

  public token: string;

  constructor(private http: HttpClient,
              private route: ActivatedRoute) {
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

  getUserRoles(): string[] {
    const jwtPayload = JSON.parse(atob(this.getCurrentUser().token.split('.')[1])) as JWTPayload;
    return JSON.parse(jwtPayload.sub) as string[];
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return token && tokenNotExpired(null, token);
  }

  isAdmin(): boolean {
    return this.getUserRoles().includes('ADMIN');
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

  getTokenFromUrl(): string {
    const token = null;
    this.route.queryParams.subscribe(params=> this.token = params['token']);
    return token;
  }
}

export interface JWTPayload {
  exp: number;
  iss: string;
  sub: string;
}
