import {Component, OnInit, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormGroup, FormControl} from '@angular/forms';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-meal-rating',
  templateUrl: './meal-rating.component.html',
  styleUrls: ['./meal-rating.component.css']
})
export class MealRatingComponent implements OnInit {
  ratingForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<MealRatingComponent>,
              @Inject(MAT_DIALOG_DATA) private data: any) { }

  ngOnInit() {
    this.ratingForm = this.buildIdeaForm();
  }

  buildIdeaForm() {
    return new FormGroup({
      myRatingControl: new FormControl('')
    })
  }
}
