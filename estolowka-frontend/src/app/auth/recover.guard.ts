import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthenticationService} from "./authentication.service";
import {tokenNotExpired} from "angular2-jwt";

@Injectable({
  providedIn: 'root'
})
export class RecoverGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {

  }

  canActivate() {
    const token = this.authenticationService.getTokenFromUrl();
    if (token && tokenNotExpired(null, token)) {
      return true;
    }
    this.router.navigate(['']);
    return false;
  }
}
