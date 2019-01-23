import {Component, OnInit} from '@angular/core';
import {UploadService} from "../upload.service";
import { MatSnackBar, MatSnackBarConfig } from '@angular/material';

@Component({
  selector: 'app-upload',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css']
})
export class UploadFormComponent implements OnInit {
  fileToUpload: File = null;
  fileName: string;

  constructor(private uploadService: UploadService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  onFileSelected(event) {
    this.fileToUpload = event.target.files[0];
    this.fileName = this.fileToUpload.name;
  }

  onUpload() {
    const config = new MatSnackBarConfig;
    config.duration = 2000;

    this.uploadService.uploadFile(this.fileToUpload).subscribe(event=>{
      console.log(event);
      config.panelClass = ['success-msg'];
      this.snackBar.open('Wysłano plik!', '', config);
    },
    err => {
      config.panelClass = ['error-msg'];
      this.snackBar.open('Wystąpił błąd ' + err.message, '', config);
    }
    );
  }
}
