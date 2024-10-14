import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs';
import { User } from '../models/auth/auth-interface';
import { Router } from '@angular/router';
import { NotificationService } from '../../services/notification/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
    private router: Router,
    private notificationService : NotificationService
  ){}

  signinWhithIdentificationAndPassword (document: number, password: string): Observable<User> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    const body = {
      document,
      password
    }
    return this.http.post<User>(`${this.apiUrl}/login`, body, {headers});
  }

  saveRoleLogged(role: string, name:string) {
    if(role) {
      localStorage.setItem('isLoggedActive', 'true');
      localStorage.setItem('loggedRole', role);
      localStorage.setItem('userName', name);
    }
  }

  saveIdUser(id: string) {
    if(id) {
      localStorage.setItem('idUser', id);
    }
  }

  getNameUser() {
    return localStorage.getItem('userName')
  }

  getIdUser():number {
    let idUser = localStorage.getItem('idUser')
    return Number(idUser);
  }

  getRoleLogged(): string | null {
  return localStorage.getItem('loggedRole')
  }

  isLogged() {
    return localStorage.getItem('isLoggedActive') === 'true';
  }

  logoutUser() {
    localStorage.removeItem('isLoggedActive');
    localStorage.removeItem('loggedRole');
    this.router.navigate(['/signin']);
    this.notificationService.success('Se ha cerrado su cesión de manera exitosa', 'Exito')
  }
}