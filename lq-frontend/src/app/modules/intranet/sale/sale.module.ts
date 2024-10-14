import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { SaleRoutingModule } from './sale-routing.module';
import { SaleComponent} from './pages/sale/sale.component';
import { FormsModule } from '@angular/forms';
import { CardTotalSalesComponent } from './components/card-total-sales/card-total-sales.component';
import { CardDaySaleComponent } from './components/card-day-sale/card-day-sale.component';
import { CardWeekSalesComponent } from './components/card-week-sales/card-week-sales.component';
import { CardMonthSalesComponent } from './components/card-month-sales/card-month-sales.component';
import { CardFilterSalesComponent } from './components/card-filter-sales/card-filter-sales.component';


@NgModule({
  declarations: [
    SaleComponent,
    CardTotalSalesComponent,
    CardDaySaleComponent,
    CardWeekSalesComponent,
    CardMonthSalesComponent,
    CardFilterSalesComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    FormsModule,
    SaleRoutingModule,  
  ],
  providers: [],
})
export class SaleModule { }