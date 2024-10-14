import { Component, OnInit } from '@angular/core';
import { Combo } from 'src/app/core/models/combos/combos.interface';
import { CombosService } from 'src/app/core/services/combos/combos.service';
import { NotificationService } from 'src/app/core/services/notification/notification.service';

@Component({
  selector: 'app-combos',
  templateUrl: './combos.component.html',
  styleUrls: ['./combos.component.css']
})
export class CombosComponent implements OnInit {

  loadingGetCombos: boolean = false;
  loadingUpdateCombo: boolean = false;
  allCombos!: Combo[];

  constructor(
    private combosService: CombosService,
    private notificationService : NotificationService
  ) {}
  ngOnInit(): void {
    this.getAllCombos();
  }

  getAllCombos() {
    this.loadingGetCombos = true;
    this.combosService.getAllCombos().subscribe(res => {
      this.loadingGetCombos = false;
      if(res){
        this.allCombos = res;
      }else {
        this.notificationService.error('No se encontraron combos disponibles', 'Error');
      }
    }, error => {
      this.loadingGetCombos = false;
      this.notificationService.error('Error al obtener los combos', 'Error');
    })
  }

  chageStatuCombo(idCombo: number) {
    this.loadingUpdateCombo = true;
    this.combosService.chageStatusCombo(idCombo).subscribe(res => {
      this.loadingUpdateCombo = false;
      this.notificationService.success('Se ha actualizado el estado del combo correctamente');
    }, error => {
      this.loadingGetCombos = false;
      this.notificationService.error('Ha ocurrido un error al actualizar el estado del producto')
    })
  } 

}
