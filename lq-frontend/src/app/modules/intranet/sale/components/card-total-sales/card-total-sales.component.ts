import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card-total-sales',
  templateUrl: './card-total-sales.component.html',
  styleUrls: ['./card-total-sales.component.css']
})
export class CardTotalSalesComponent {
  @Input() sales!: number;
  @Input() loadingSales: boolean = false;
}
