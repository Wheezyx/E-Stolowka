import {Component, OnInit} from '@angular/core';
import {RecoverService} from "../recover.service";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  password: string;

  constructor(private recoverService: RecoverService) {
  }

  ngOnInit() {
  }

  resetPassword() {
    this.recoverService.resetPassword(this.password);
  }
}
