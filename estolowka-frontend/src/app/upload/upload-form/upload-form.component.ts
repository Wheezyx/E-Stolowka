import {Component, OnInit} from '@angular/core';
import {UploadService} from "../upload.service";

@Component({
  selector: 'app-upload',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css']
})
export class UploadFormComponent implements OnInit {
  fileToUpload: File = null;

  constructor(private uploadService: UploadService) {
  }

  ngOnInit() {
  }

  onFileSelected(event) {
    this.fileToUpload = event.target.files[0];
  }

  onUpload() {
    this.uploadService.uploadFile(this.fileToUpload).subscribe(event=>{
      console.log(event);
    });
  }
}
