import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  canActivate() {
    if (this.authenticationService.isAuthenticated() &&
      this.authenticationService.isAdmin()) {
      return true;
    }
    this.router.navigateByUrl('');
    return false;
  }
}
