import {Component, OnInit} from '@angular/core';
import {UploadService} from "../upload.service";

@Component({
  selector: 'app-upload',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css']
})
export class UploadFormComponent implements OnInit {
  fileToUpload: File = null;
  progress: { percentage: number } = {percentage: 0};

  constructor(private uploadService: UploadService) {
  }

  ngOnInit() {
  }

  onFileSelected(event) {
    this.fileToUpload = event.target.files[0];
  }

  onUpload() {
    this.progress.percentage = 0;
    this.uploadService.uploadFile(this.fileToUpload).subscribe(event=>{
      console.log(event);
    });
  }
}
