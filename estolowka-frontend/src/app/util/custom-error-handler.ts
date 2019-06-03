import {ErrorHandler, Injectable} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';

@Injectable()
export class CustomErrorHandler implements ErrorHandler {
  message: string;

  constructor(private snackBar: MatSnackBar) {
  }

  handleError(error: Error | HttpErrorResponse) {

    if (error instanceof HttpErrorResponse) {
      // Server or connection error happened
      if (!navigator.onLine) {
        // Handle offline error
        this.message = "Error " + error.error.status + ": no internet connection";
        this.openErrorMessage(this.message);
      } else {
        // Handle Http Error (error.status === 403, 404...)
        this.message = "Error " + error.error.status + ": " + error.error.message;
        this.openErrorMessage(this.message);
      }
    } else {
      this.openErrorMessage(error.message);
    }
  }

  openErrorMessage(message: string) {
    const config = new MatSnackBarConfig;
    config.duration = 2000;
    config.panelClass = ['error-msg'];
    this.snackBar.open(message, '', config);
  }

}
