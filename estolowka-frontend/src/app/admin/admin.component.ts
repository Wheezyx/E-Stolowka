import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  constructor(private router: Router) { }

  navigateToUsersPage(): void {
    this.router.navigateByUrl('/users');
  }

  navigateToUploadUsersPage(): void {
    this.router.navigateByUrl('/usersUpload');
  }

  navigateToAddMenuPage(): void {
    this.router.navigateByUrl('/menu');
  }
}
