import { Component, Input } from '@angular/core';
import { SalesMonth } from 'src/app/core/models/sales/sales.interface';

@Component({
  selector: 'app-card-month-sales',
  templateUrl: './card-month-sales.component.html',
  styleUrls: ['./card-month-sales.component.css']
})
export class CardMonthSalesComponent {
  @Input() loadingSales: boolean = false;
  @Input() sale!: SalesMonth;
}
