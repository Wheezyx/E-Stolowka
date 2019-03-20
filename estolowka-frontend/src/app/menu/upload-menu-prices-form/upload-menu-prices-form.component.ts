import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MenuService} from "../menu.service";

@Component({
  selector: 'app-upload-menu-prices-form',
  templateUrl: './upload-menu-prices-form.component.html',
  styleUrls: ['./upload-menu-prices-form.component.css']
})
export class UploadMenuPricesFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private menuService: MenuService) {
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
    this.menuService.updateMenuPrices(this.form.value).subscribe(() => this.refresh());
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
}
