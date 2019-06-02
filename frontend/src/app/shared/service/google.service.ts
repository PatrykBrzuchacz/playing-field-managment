
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GooglePlaceMap } from '../model/google-map';

@Injectable({
  providedIn: 'root'
})
export class GoogleService {

  constructor(private http: HttpClient) {
  }

  getGooglePlaces(lat: number, lng: number): Observable<any[]> {

    const params = new HttpParams()
    .set('lat', "50" )
    .set('lng', "20");
    return this.http.get<any[]>('/api/searchByLocation', { params: params });
  }
}
