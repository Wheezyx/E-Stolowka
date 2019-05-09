import {Component} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { MealRatingComponent } from '../meal-rating.component';

@Component({
  selector: 'app-rating-dialog',
  template: ''
})
export class MealRatingDialogComponent {
  dialogRef: MatDialogRef<MealRatingComponent>;

  constructor(private dialog: MatDialog) {}

  openDialog(orderId: number): void {
    this.dialogRef = this.dialog.open(MealRatingComponent, {
      data: { id: orderId },
      width: '22%',
      height: '22%'
    });
  }
}
