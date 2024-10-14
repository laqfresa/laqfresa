import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DetailOrder, OrderResponse } from 'src/app/core/models/orders/orders.interface';

@Component({
  selector: 'app-modal-info-historic-sale',
  templateUrl: './modal-info-historic-sale.component.html',
  styleUrls: ['./modal-info-historic-sale.component.css']
})
export class ModalInfoHistoricSaleComponent implements OnInit {

  @Input() ordersCompleted!: OrderResponse;
  @Input() isVisible: boolean = false;
  @Output() hideModalEmitter = new EventEmitter<boolean>();
  detailOrder!: DetailOrder[];

  ngOnInit(): void {
    this.createDetailsOrder(this.ordersCompleted);
  }

  createDetailsOrder(order: OrderResponse) {
    this.detailOrder = order.detailOrders;
  }

  hideModalEmit() {
    this.hideModalEmitter.emit(false);
  }
}
