import {Component, Input, OnInit} from '@angular/core';
import {Day} from '../model/day';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OrderListComponent} from "../order-list/order-list.component";

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnInit {

  @Input() order: Day;

  minDate = this.getTomorrowDate();
  maxDate = this.getLastDayOfMonth();

  form: FormGroup;

  dateFilter = (date: Date) => !this.orderList.isExist(date);

  constructor(private formBuilder: FormBuilder, private orderList: OrderListComponent) {
  }

  get meals(): FormArray { return this.form.get('meals') as FormArray; }

  ngOnInit() {
    this.form = this.formBuilder.group({
      dateOfOrder: ['', Validators.required],
      meals: this.formBuilder.array(this.order.meals)
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

  get mealTypeNames(): { [key: string]: string } {
    return {
      'BREAKFAST': 'Śniadanie',
      'DINNER': 'Obiad',
      'SUPPER': 'Kolacja'
    };
  }
}
