import { Component } from '@angular/core';
import { CalendarView } from 'angular-calendar';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'calendar-component',
  styleUrls: ['./calendar.component.css'],
  templateUrl: './calendar.component.html'
})
export class CalendarComponent {
  form: FormGroup;

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.buildIdeaForm();
  }

  buildIdeaForm() {
    return this.formBuilder.group({});
  }
}