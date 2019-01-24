import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from "./authentication.service";
import {tokenNotExpired} from "angular2-jwt";

@Injectable({
  providedIn: 'root'
})
export class RecoverGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    var token = route.queryParams['token'];
    if (token && tokenNotExpired(null, token)) {
      return true;
    }
    this.router.navigate(['']);
    return false;
  }
}
