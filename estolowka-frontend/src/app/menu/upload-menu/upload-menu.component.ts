import {Component, Input, OnInit} from '@angular/core';
import {MenuService} from "../menu.service";
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {WeeklyMenuItem} from '../model/weekly-menu-item';
import {CustomErrorHandler} from '../../util/custom-error-handler';

@Component({
  selector: 'app-upload-menu',
  templateUrl: './upload-menu.component.html',
  styleUrls: ['./upload-menu.component.css']
})
export class UploadMenuComponent implements OnInit {

  @Input() menuItems: WeeklyMenuItem[] = [];

  constructor(private menuService: MenuService,
              private snackBar: MatSnackBar,
              private errorHandler: CustomErrorHandler) {
  }

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
        this.errorHandler.handleError(err);
      });
  }

  openSuccessMessage() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['success-msg'];
    this.snackBar.open('Menu zostało zaktualizowane!', '', config);
  }
}
