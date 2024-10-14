import { Component, Input } from '@angular/core';
import { SalesDaily } from 'src/app/core/models/sales/sales.interface';

@Component({
  selector: 'app-card-day-sale',
  templateUrl: './card-day-sale.component.html',
  styleUrls: ['./card-day-sale.component.css']
})
export class CardDaySaleComponent {
  @Input() loadingSales: boolean = false;
  @Input() sale!: SalesDaily;
}
