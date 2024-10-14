import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HistoricSaleComponent } from './pages/historic-sale/historic-sale.component';

const routes: Routes = [
  {
    path: '',
    component: HistoricSaleComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HistoricSaleRoutingModule { }