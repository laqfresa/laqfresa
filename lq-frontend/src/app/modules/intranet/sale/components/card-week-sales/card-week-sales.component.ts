import { Component, Input } from '@angular/core';
import { SalesWeek } from 'src/app/core/models/sales/sales.interface';

@Component({
  selector: 'app-card-week-sales',
  templateUrl: './card-week-sales.component.html',
  styleUrls: ['./card-week-sales.component.css']
})
export class CardWeekSalesComponent {
  @Input() loadingSales: boolean = false;
  @Input() sale!: SalesWeek;
}
