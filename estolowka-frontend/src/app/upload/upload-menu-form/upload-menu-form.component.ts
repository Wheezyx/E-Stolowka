import { Component, OnInit, Input } from '@angular/core';
import { WeeklyMenuItem } from '../../order/model/weekly-menu-item';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-upload-menu-form',
  templateUrl: './upload-menu-form.component.html',
  styleUrls: ['./upload-menu-form.component.css']
})
export class UploadMenuFormComponent implements OnInit {

  @Input() menuItem: WeeklyMenuItem;
  
  minDate = this.getTomorrowDate();
  maxDate = this.getLastDayOfMonth();

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.buildMenuForm();
    console.log(this.form)
  }

  buildMenuForm() {
    return this.formBuilder.group({
      date: ['', Validators.required],
      breakfast: ['', Validators.required],
      dinner: ['', Validators.required],
      supper: ['', Validators.required],
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

}
