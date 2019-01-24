import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule, MatDialogModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { EmailFormComponent } from './recover/email-form/email-form.component';
import { EmailDialogComponent } from './recover/email-form/email-dialog/email-dialog.component';
import { PasswordComponent } from './recover/password/password.component';

@NgModule({
 declarations: [
   LoginComponent,
   EmailFormComponent,
   EmailDialogComponent,
   PasswordComponent
 ],
 imports: [
   CommonModule,
   MatCardModule,
   MatButtonModule,
   FormsModule ,
   MatDialogModule,
   MatFormFieldModule,
   MatInputModule,
   ReactiveFormsModule
 ],
exports: [
 LoginComponent
 ]
})
export class LoginModule { }
