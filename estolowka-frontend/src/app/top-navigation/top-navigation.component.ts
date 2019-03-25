import {Component} from '@angular/core';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './top-navigation.component.html',
  styleUrls: ['./top-navigation.component.css']
})
export class TopNavigationComponent {


  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  logout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl('/login');
  }

  isUserLoggedIn(): boolean {
    return this.authenticationService.isAuthenticated();
  }

  getCurrentUserEmail(): string {
    return this.authenticationService.getCurrentUserEmail();
  }

  isAdmin(): boolean {
    let email = this.getCurrentUserEmail();
    return email === "admin@gmail.com";
  }

  navigateToMainPage(): void {
    this.router.navigateByUrl('');
  }

  navigateToAdminPage(): void {
    this.router.navigateByUrl('/admin');

  }
}