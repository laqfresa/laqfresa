import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent {

  width = window.innerWidth;

  isVisible!: boolean;

  /**
   * Escucha los eventos del cambio del ancho de la pantalla y asigna el valor de width
   * @param event Evento con los cambios en el ancho de la pantallla
   */
  @HostListener('window:resize', ['$event'])onResize(event: Event) {
    if(event.target instanceof Window) {
      this.width = event.target.innerWidth;
    }
  }

  showMenuSide() {
    this.isVisible = true;
  }

  hideMenuSide(){
    this.isVisible = false;
  }
}
