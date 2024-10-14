import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from "@angular/router";
import { NotificationService } from "../../services/notification/notification.service";


@Injectable ({
  providedIn: 'root'
})

export class AuthPublicGuard {

  constructor (
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  canActivate() {
    if(this.authService.isLogged()) {
      this.router.navigate([''])
      this.notificationService.warning('Es necesario cerrar sesión para acceder a esta ruta');
      return false;
    }else {
      return true;
    }
  }
}