import { Component, Input } from '@angular/core';
import { OrderResponse } from 'src/app/core/models/orders/orders.interface';

@Component({
  selector: 'app-table-historic-sale',
  templateUrl: './table-historic-sale.component.html',
  styleUrls: ['./table-historic-sale.component.css']
})
export class TableHistoricSaleComponent {
  @Input() ordersCompleted!: OrderResponse[];
  @Input() loadingHistoricSale: boolean = false;

  isvisibleModal: boolean = false;
  historicSaleModal!: OrderResponse;
  isVisibleModalEdit: boolean = false;
  loadingUpdate: boolean = false;

  constructor(
  ) {
    
  }

  showModal(ordersCompleted: OrderResponse) {
    this.historicSaleModal = ordersCompleted;
    this.isvisibleModal = true;
  }

  hideModalInfo(hide: boolean) {
    this.isvisibleModal = hide;
  }
}
