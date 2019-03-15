import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, of} from "rxjs";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RecoverGuard implements CanActivate {

  token: string;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    this.token = route.queryParams['token'];
    return this.http.get(environment.recoverUrl + '/link?token=' + this.token).pipe(
      map(response => {
        return true;
      }),
      catchError((err) => {
        this.router.navigate(['']);
        return of(false);
      })
    );
  }

  getToken(): string {
    return this.token;
  }
}
