import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';

@NgModule({
 declarations: [
   LoginComponent
 ],
 imports: [
   CommonModule,
   MatCardModule,
   MatButtonModule,
   FormsModule 
 ],
exports: [
 LoginComponent
 ]
})
export class LoginModule { }
