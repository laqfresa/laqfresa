import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderResponse, DetailProduct, DetailOrder, Ingredient, DetailAdditional } from 'src/app/core/models/orders/orders.interface';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { OrderService } from 'src/app/core/services/orders/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  ordersPending!: OrderResponse[];
  loadingOrders: boolean = false;
  renderPageDetail: boolean = false;
  order!: OrderResponse;

  products!: DetailOrder[]
  toppingsClasic!: DetailProduct[];
  toppingsPremium!: DetailProduct[];
  Sauces!: DetailProduct[];
  aditional!: DetailProduct[];

  constructor(
    private orderService : OrderService,
    private notificationService : NotificationService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.getOrderPending();
  }

  getOrderPending() {
    this.loadingOrders = true;
    this.orderService.getAllOrdersPendings().subscribe(res => {
      if(res) {
        this.ordersPending = res;
      }else {
        this.notificationService.info('No se encontraron ordenes pendientes')
      }
      this.loadingOrders = false;
    }, error => {
      this.notificationService.error('Ocurrio un error al obtener las ordenes pendientes');
      this.loadingOrders = false;
    })
  }
  
  updateOrderById(idOrder: number) {
    this.loadingOrders = true;
    this.orderService.updateOrderById(idOrder).subscribe(res => {
      this.notificationService.success('¡Pedido actualizado exitosamente!', 'Éxito');
      window.location.reload();
    }, error => {
      this.loadingOrders = false;
      this.notificationService.error('Ha ocurrido un error al actualizar el pedido. Contacte a Tecnología.')
    });
  }

  cancelUpdate() {
    this.renderPageDetail = false;
  }

  viewOrderDetail(id: number) {
    this.orderService.getOrderPending(id).subscribe(res => {
      if(res) {
        this.router.navigate(['/intranet/orders/order', id]); // Redirige a la ruta con el ID del pedido
      }else {
        this.notificationService.info('Este producto ya fue tomado');
      }
    })
  }

}
