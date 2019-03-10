import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UploadService } from "../upload.service";
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { WeeklyMenuItem } from '../../order/model/weekly-menu-item';

@Component({
  selector: 'app-upload-menu',
  templateUrl: './upload-menu.component.html',
  styleUrls: ['./upload-menu.component.css']
})
export class UploadMenuComponent implements OnInit {

  form: FormGroup;
  menuItems: WeeklyMenuItem[] = [];

  minDate = this.getTomorrowDate();
  maxDate = this.getLastDayOfMonth();

  constructor(private formBuilder: FormBuilder,
              private uploadService: UploadService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.menuItems = this.generateMenuMeals();
    this.form = this.buildMenuForm();
  }

  buildMenuForm() {
    return this.formBuilder.group({
      date: ['', Validators.required],
      breakfast: ['', Validators.required],
      dinner: ['', Validators.required],
      supper: ['', Validators.required],
    });
  }

  generateMenuMeals(): WeeklyMenuItem[] {
    for (let i = 0; i < 7; i++) {
      this.menuItems.push(new WeeklyMenuItem());
    }
  
    return this.menuItems;
  }

  sendMenu() {
    this.uploadService.sendMenu(this.form.value).subscribe(event => {
        this.openSuccessMessage();
      },
      err => {
        console.log('error occurred: ' + err.message);
        this.openErrorMessage(err);
      });
  }

  getTomorrowDate() {
    let today = new Date();
    return new Date(today.getFullYear(), today.getMonth(), today.getDate()+1);
  }

  getLastDayOfMonth() {
    let today = new Date();
    return new Date(today.getFullYear(), today.getMonth()+1, 0);
  }

  openSuccessMessage() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['success-msg'];
    this.snackBar.open('Menu zostało zaktualizowane!', '', config);
  }

  openErrorMessage(error: any) {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['error-msg'];
    this.snackBar.open('Wystąpił błąd ' + error.message, '', config);
  }
}
