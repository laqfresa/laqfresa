import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card-filter-sales',
  templateUrl: './card-filter-sales.component.html',
  styleUrls: ['./card-filter-sales.component.css']
})
export class CardFilterSalesComponent {
  @Input() loadingSales: boolean = false;
  @Input() sale: number = 0;
}
