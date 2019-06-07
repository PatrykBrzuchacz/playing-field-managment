
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { debounceTime } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class GoogleService {

  constructor(private http: HttpClient) {
  }
  k: string;
  getGooglePlaces(lat: number, lng: number): Observable<any[]> {

    const params = new HttpParams()
    .set('lat', `${lat}`)
    .set('lng', `${lng}`);

    return this.http.get<any[]>('/api/searchByLocation', { params: params });

  }
}
