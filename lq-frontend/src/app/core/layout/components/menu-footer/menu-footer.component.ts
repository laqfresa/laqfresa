import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { RolesLQFresa } from 'src/app/core/authentication/models/auth/auth-interface';
import { AuthService } from 'src/app/core/authentication/services/auth.service';

@Component({
  selector: 'app-menu-footer',
  templateUrl: './menu-footer.component.html',
  styleUrls: ['./menu-footer.component.css']
})
export class MenuFooter {

  width = window.innerWidth;

  @Input() isVisible!: boolean;

  @Output() showMenuSide = new EventEmitter<boolean>();
  @Output() hideSideMenu = new EventEmitter<boolean>();
  roles = RolesLQFresa;
  role!: string | null;

  constructor(
    private authService : AuthService,
  ) {
    this.role = this.authService.getRoleLogged();
  }

  /**
   * Escucha los eventos del cambio del ancho de la pantalla y asigna el valor de width
   * @param event Evento con los cambios en el ancho de la pantallla
   */
  @HostListener('window:resize', ['$event'])onResize(event: Event) {
    if(event.target instanceof Window) {
      this.width = event.target.innerWidth;
    }
  }

  showMenu() {
    this.showMenuSide.emit(true);
  }

  hideMenu(){
    document.getElementById("menuContainer")?.classList.remove("menuContainerAnimIN");
    document.getElementById("menuContainer")?.classList.add("menuContainerAnimOUT");
    setTimeout(() => {
      this.isVisible = false;
      this.hideSideMenu.emit(false);
    }, 600);
  }
}
