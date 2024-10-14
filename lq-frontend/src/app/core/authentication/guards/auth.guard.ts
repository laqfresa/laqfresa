import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { NotificationService } from "../../services/notification/notification.service";

@Injectable ({
  providedIn: 'root'
})

export class AuthGuard {

  constructor (
    private authService: AuthService,
    private router: Router,
    private notificationService : NotificationService
  ) {}

  canActivate() {
    let isLogged: boolean = false;
    if(this.authService.isLogged()) {
      isLogged = true;
    } else {
      isLogged = false;
      this.router.navigate(['/signin'])
      this.notificationService.warning('Es necesario Iniciar sesión para acceder a esta ruta');
    }
    return isLogged;
  }
}