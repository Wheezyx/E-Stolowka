import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment";
import { WeeklyMenuItem } from '../order/model/weekly-menu-item';

@Injectable()
export class UploadService {

  constructor(private _http: HttpClient) {
  }

  uploadFile(file: File): Observable<HttpEvent<{}>> {
    let formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', environment.uploadUrl, formData, {
      reportProgress: true,
      responseType: 'text'
    });

    return this._http.request(req);
  }

  sendMenu(menu: WeeklyMenuItem[]): Observable<WeeklyMenuItem[]> {
    return this._http.post<WeeklyMenuItem[]>(environment.menuUrl, menu);
  }

  getMenu(): Observable<WeeklyMenuItem[]> {
    return this._http.get<WeeklyMenuItem[]>(environment.menuUrl);
  }
}
