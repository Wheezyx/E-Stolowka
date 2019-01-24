import {Component, OnInit} from '@angular/core';
import { MatDialogRef} from "@angular/material";
import {RecoverService} from "../recover.service";

@Component({
  selector: 'app-email-form',
  templateUrl: './email-form.component.html',
  styleUrls: ['./email-form.component.css']
})
export class EmailFormComponent implements OnInit {

  email: string = '';

  constructor(private recoverService: RecoverService,
              private dialog: MatDialogRef<EmailFormComponent>) {
  }

  ngOnInit() {
  }

  sendRecoverLink() {
    this.recoverService.recoverLink(this.email).subscribe(any => this.dialog.close());
  }
}
