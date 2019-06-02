
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

  getGooglePlaces(googlePlaceMap: GooglePlaceMap): Observable<any[]> {
    console.log(googlePlaceMap.lat);
    const params = new HttpParams()
    .set('lat', `${googlePlaceMap.lat}`)
    .set('lng', `${googlePlaceMap.lng}`);
    return this.http.get<any[]>('/api/searchByLocation', { params: params });
  }
}
