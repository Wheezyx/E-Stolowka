import {Component, OnInit} from '@angular/core';
import {UploadService} from "../upload.service";
import {MatSnackBar, MatSnackBarConfig}  from '@angular/material';
import { CustomErrorHandler } from '../../util/custom-error-handler';

@Component({
  selector: 'app-upload',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css']
})
export class UploadFormComponent implements OnInit {
  fileToUpload: File = null;
  fileName: string;

  constructor(private uploadService: UploadService,
              private snackBar: MatSnackBar,
              private errorHandler: CustomErrorHandler) {
  }

  ngOnInit() {
  }

  onFileSelected(event) {
    this.fileToUpload = event.target.files[0];
    this.fileName = this.fileToUpload.name;
  }

  onUpload() {
    this.uploadService.uploadFile(this.fileToUpload).subscribe(event=>{
      console.log(event);
      this.openSuccessMessage();
    },
    err => {
      this.errorHandler.handleError(err);
    });
  }

  openSuccessMessage() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['success-msg'];
    this.snackBar.open('Wysłano plik!', '', config);
  }
}
