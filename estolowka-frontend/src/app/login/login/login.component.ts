import {Component, OnInit, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../auth/authentication.service';
import {EmailDialogComponent} from "../recover/email-form/email-dialog/email-dialog.component";

@Component({
 selector: 'app-login',
 templateUrl: './login.component.html',
 styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild(EmailDialogComponent) emailDialog: EmailDialogComponent;

 model: any = {};
 loading = false;
 error = '';

 constructor(private router: Router,
             private authenticationService: AuthenticationService) {
 }

 ngOnInit() {
   this.authenticationService.logout();
 }

 login(): void {
   this.loading = true;
   this.authenticationService.login(
     {email: this.model.email, password: this.model.password},
     () => {
       this.loading = false;
       this.router.navigateByUrl('/');
     },
     (error) => {
       switch(error.status) {
         case 401: {
           this.error = "Niepoprawny login lub hasło.";
           break;
         }
         default: {
           this.error = "Oops! Coś poszło nie tak.";
         }
       }
       this.loading = false;
     });
 }

 openDialog() {
   this.emailDialog.openDialog();
 }
}
