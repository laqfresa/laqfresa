import { Component } from '@angular/core';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { OrderService } from 'src/app/core/services/orders/orders.service';
import { OrderResponse } from 'src/app/core/models/orders/orders.interface';

@Component({
  selector: 'app-historic-sale',
  templateUrl: './historic-sale.component.html',
  styleUrls: ['./historic-sale.component.css']
})
export class HistoricSaleComponent {
  loadingHistoricSale: boolean = false;
  ordersCompleted!: OrderResponse[];

  constructor(
    private orderService : OrderService,
    private notificationService : NotificationService
  ) {
    this.getAllOrderCompleted();
  }

  getAllOrderCompleted() {
    this.loadingHistoricSale = true;
    this.orderService.getAllOrdersCompleted().subscribe(res => {
      this.loadingHistoricSale = false;
      if(res) {
        this.ordersCompleted = res;
      }
    })
  }
}
