import {Component, OnInit, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormGroup, FormControl} from '@angular/forms';
import {FormBuilder} from '@angular/forms';
import {OrderService} from "../../order/service/order.service";

@Component({
  selector: 'app-meal-rating',
  templateUrl: './meal-rating.component.html',
  styleUrls: ['./meal-rating.component.css']
})
export class MealRatingComponent implements OnInit {
  ratingForm: FormGroup;
  currentOrderId: number;
  error: string = '';

  constructor(private formBuilder: FormBuilder,
              private orderService: OrderService,
              private dialogRef: MatDialogRef<MealRatingComponent>,
              @Inject(MAT_DIALOG_DATA) private data: any) {
    this.currentOrderId = data.id;
  }

  ngOnInit() {
    this.ratingForm = this.buildIdeaForm();
  }

  buildIdeaForm() {
    return new FormGroup({
      myRatingControl: new FormControl('')
    })
  }

  rateMeal() {
    this.orderService.rateMeal(this.currentOrderId, this.ratingForm.get('myRatingControl').value).subscribe(() => {
      console.log("rate sent: " + this.ratingForm.value);
    }, (error) => {
      this.error = error.error.message;
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
