import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Combo } from 'src/app/core/models/combos/combos.interface';

@Component({
  selector: 'app-table-combos',
  templateUrl: './table-combos.component.html',
  styleUrls: ['./table-combos.component.css']
})
export class TableCombosComponent {
  @Input() allCombos!: Combo[];
  @Input() loadingCombos: boolean = false;
  @Output() changeStatusEmit = new EventEmitter<number>()
  isVisibleModalEdit: boolean = false;

  changeStatusCombo(idCombo: number) {
    this.changeStatusEmit.emit(idCombo);
  }

  showModalEdit() {
    this.isVisibleModalEdit = true;
  }
}
