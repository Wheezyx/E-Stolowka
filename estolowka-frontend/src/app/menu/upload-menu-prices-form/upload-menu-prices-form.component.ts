import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MenuService} from "../menu.service";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material";
import { CustomErrorHandler } from '../../util/custom-error-handler';

@Component({
  selector: 'app-upload-menu-prices-form',
  templateUrl: './upload-menu-prices-form.component.html',
  styleUrls: ['./upload-menu-prices-form.component.css']
})
export class UploadMenuPricesFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private menuService: MenuService,
              private snackBar: MatSnackBar,
              private errorHandler: CustomErrorHandler) {
  }

  ngOnInit() {
    this.buildForm();
    this.refresh();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      breakfastPrice: ['', Validators.required],
      dinnerPrice: ['', Validators.required],
      supperPrice: ['', Validators.required],
    });
  }

  updatePrices() {
    this.menuService.updateMenuPrices(this.form.value).subscribe(event => {
      console.log(event);
      this.openSuccessMessage();
      this.refresh();
    }, err => {
      this.errorHandler.handleError(err);
    });

  }

  refresh() {
    this.menuService.getMenuPrices().subscribe(data => {
      this.form.setValue({
        breakfastPrice: data.breakfastPrice,
        dinnerPrice: data.dinnerPrice,
        supperPrice: data.supperPrice
      })
    })
  }

  openSuccessMessage() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['success-msg'];
    this.snackBar.open('Cennik został zaktualizowany!', '', config);
  }
}
