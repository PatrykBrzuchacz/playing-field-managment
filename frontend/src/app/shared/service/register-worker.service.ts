import { HttpParams, HttpHeaders, } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User, UserCredentials} from '../model';

import {HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { WorkerRequest } from '../model/worker-request';


const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class RegisterWorkerService {

  public user: User;
  constructor(private http: HttpClient) { }

  registerWorker(userCredentials: UserCredentials, file:File){
    let formData = new FormData();
    formData.append('file', file);
    formData.append('userCredentials', '{ "username": "'+  userCredentials.username +'",'+ '"password":"'+userCredentials.password + '"}')

    return this.http
      .post(`${API_URL}/workerRequests/worker/signup`, formData
      );
  }
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
}