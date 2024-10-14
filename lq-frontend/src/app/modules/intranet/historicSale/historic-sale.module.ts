import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { TableHistoricSaleComponent } from './components/table-historic-sale/table-historic-sale.component';
import { HistoricSaleRoutingModule } from './historic-sale-routing.module';
import { HistoricSaleComponent} from './pages/historic-sale/historic-sale.component';
import { ModalInfoHistoricSaleComponent } from './components/modal-info-historic-sale/modal-info-historic-sale.component';


@NgModule({
  declarations: [
    HistoricSaleComponent,
    TableHistoricSaleComponent,
    ModalInfoHistoricSaleComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    HistoricSaleRoutingModule,  
  ],
  providers: [],
})
export class HistoricSaleModule { }