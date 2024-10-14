import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CombosRoutingModule } from './combos-routing.module';
import { TableCombosComponent } from './components/table-combos/table-combos.component';
import { CombosComponent } from './pages/combos/combos.component';
import { ModalEditComboComponent } from './components/modal-edit-combo/modal-edit-combo.component';

@NgModule({
  declarations: [
    TableCombosComponent,
    CombosComponent,
    ModalEditComboComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    CombosRoutingModule,  
  ],
  providers: [],
})
export class CombosModule { }