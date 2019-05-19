import { HttpParams, HttpHeaders, } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User, UserCredentials} from '../model';

import {HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';


const API_URL = environment.apiUrl;

const HttpUploadOptions = {
  headers: new HttpHeaders({ "Accept": "application/json" })
}
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

    console.log(formData);
    console.log(file);
    
    return this.http
      .post(`${API_URL}/workerRequests/worker/signup`, formData
      );
  }


  deleteUser(userId: number) {
    return this.http.delete(`${API_URL}/users/${userId}`);
  }

  banUser(id: number) {
    return this.http.put(`${API_URL}/users/${id}/ban`, this.user);
  }

  unbanUser(id: number) {
    return this.http.put<User>(`${API_URL}/users/${id}/unban`, this.user);
  }
}