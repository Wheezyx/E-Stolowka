import { Component, OnInit, Input } from '@angular/core';
import { Day } from '../model/day';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnInit {

  @Input() order: Day;

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  get meals(): FormArray { return this.form.get('meals') as FormArray; }

  ngOnInit() {
    this.form = this.formBuilder.group({
      dateOfOrder: ['', Validators.required],
      meals: this.formBuilder.array(this.order.meals)
    })

    console.log(this.form)
  }
}