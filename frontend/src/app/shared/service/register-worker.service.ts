import { HttpParams, HttpHeaders, } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User, UserCredentials} from '../model';

import {HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { WorkerRequest } from '../model/worker-request';
import { PlayingField } from '../model/google-map';


const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class RegisterWorkerService {

  public user: User;
  constructor(private http: HttpClient) { }

  // registerWorker(userCredentials: UserCredentials, file:File){
  //   let formData = new FormData();
  //   formData.append('file', file);
  //   formData.append('userCredentials', '{ "username": "'+  userCredentials.username +'",'+ '"password":"'+userCredentials.password + '"}')

  //   return this.http
  //     .post(`${API_URL}/workerRequests/worker/signup`, formData
  //     );
  // }

  getWorkerRequests(): Observable<WorkerRequest[]> {
    return this.http.get<WorkerRequest[]>(`${API_URL}/workerRequests`);
  }
  acceptWorkerRequest(id: number) {
    const params = { "decision" : "true"};
    return this.http.put<any>(`${API_URL}/workerRequests/` + id, null, {params} );
  }

  declineWorkerRequest(id: number) {
    const params = { "decision" : "false"};
    return this.http.put<any>(`${API_URL}/workerRequests/` + id, null, {params} );
  }
  sendRequestToAssignPF(pf: PlayingField, file:File){
    let formData = new FormData();
    formData.append('file', file);
    formData.append('playingField', '{ "apiId": "'+  pf.apiId +'",'+ '"name": "'+ pf.name + '",'+'"lat":"'+pf.location.lat+ '",'+
    '"lng":"'+pf.location.lng +'",'+  '"formattedAddress":"'+pf.location.formattedAddress + '"}');

    return this.http.post(`${API_URL}/assignToWorker`, formData);
  }
  sendRequestToAssignPFAndRegister(pf: PlayingField, file:File, login: string, password: string): Observable<any> {
    let formData = new FormData();
    formData.append('file', file);
    formData.append('playingField', '{ "apiId": "'+  pf.apiId +'",'+ '"name": "'+ pf.name + '",'+'"lat":"'+pf.location.lat+ '",'+
    '"lng":"'+pf.location.lng +'",'+  '"formattedAddress":"'+pf.location.formattedAddress + '"}');
    formData.append('username', login);
    formData.append('password', password);

    return this.http.post(`${API_URL}/assignToWorkerAndRegister`, formData);
  }
}
