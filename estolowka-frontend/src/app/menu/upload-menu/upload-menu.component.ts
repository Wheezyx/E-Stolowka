import { Component, OnInit, Input } from '@angular/core';
import { MenuService } from "../menu.service";
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { WeeklyMenuItem } from '../model/weekly-menu-item';

@Component({
  selector: 'app-upload-menu',
  templateUrl: './upload-menu.component.html',
  styleUrls: ['./upload-menu.component.css']
})
export class UploadMenuComponent implements OnInit {

  @Input() menuItems: WeeklyMenuItem[] = [];

  constructor(private menuService: MenuService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.menuItems = this.generateMenuMeals();
  }

  generateMenuMeals(): WeeklyMenuItem[] {
    for (let i = 0; i < 7; i++) {
      this.menuItems.push(new WeeklyMenuItem());
    }
  
    return this.menuItems;
  }

  sendMenu() {
    this.menuService.sendMenu(this.menuItems).subscribe(event => {
        this.openSuccessMessage();
      },
      err => {
        console.log('error occurred: ' + err.message);
        this.openErrorMessage(err);
      });
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
