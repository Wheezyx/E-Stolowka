import { Injectable } from '@angular/core';
import {
 HttpRequest, HttpHandler, HttpEvent, HttpInterceptor,
 HttpErrorResponse, HttpResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

 constructor(private router: Router,
             private auth: AuthenticationService) {
 }

 intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   request = request.clone({
     setHeaders: {
       Authorization: `${this.auth.getToken()}`
     }
   });

   return next.handle(request).pipe(tap((event: HttpEvent<any>) => {
     if (event instanceof HttpResponse) {}
   }, (err: any) => {
     if (err instanceof HttpErrorResponse) {
       if (err.status === 401) {
         this.router.navigateByUrl('/login');
       }
     }
   }));
 }
}