import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material";
import {EmailFormComponent} from "../email-form.component";

@Component({
  selector: 'app-email-dialog',
  template: '',
})
export class EmailDialogComponent{

  dialogRef: MatDialogRef<EmailFormComponent>;

  constructor(private dialog: MatDialog) { }

  openDialog(): void {
    this.dialogRef = this.dialog.open(EmailFormComponent,
      {
        width: '40%',
        height: 'fit-content'
      });
  }
}
