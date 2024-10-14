import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DetailAdditional, DetailOrder, Ingredient, OrderResponse } from 'src/app/core/models/orders/orders.interface';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { OrderService } from 'src/app/core/services/orders/orders.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
  export class OrderDetailComponent {
    loadingOrder: boolean = false
    order!: OrderResponse;
    detailOrders!: DetailOrder[];
    @Output() updateEmit = new EventEmitter<number>()

    constructor(
      private orderService: OrderService,
      private route: ActivatedRoute,
      private notificationService: NotificationService,
      private router: Router
    ) {
      this.route.paramMap.subscribe(params => {
        const id = params.get('id');
        if(id) {
          this.getOrderById(id);
        }
      });
    }

    getOrderById(id: number | string) {
      this.loadingOrder = true;
      this.orderService.getOrderProgres(id).subscribe(
        (res: OrderResponse) => {
          if (res) {
            this.order = this.mapOrderResponse(res);  
            this.detailOrders = this.order.detailOrders;
            // También puedes obtener los detalles si es necesario
          } else {
            this.notificationService.error('No se encontró el pedido, por favor recargue la página');
          }
          this.loadingOrder = false;
        },
        (error) => {
          this.notificationService.error('Ocurrió un error al obtener el pedido');
          this.loadingOrder = false;
        }
      );
    }

    mapOrderResponse(response: OrderResponse): OrderResponse {
      return {
        ...response,
        detailOrders: response.detailOrders.map(detailOrder => {
          const product = detailOrder.product;
          const additionsGrouped = this.groupAdditionsByType(detailOrder.detailAdditionals);
  
          return {
            ...detailOrder, // Incluye todos los campos de DetailOrder
            product: {
              ...product,
              toppings: {
                classic: additionsGrouped['Toppings Clásicos'],
                premium: additionsGrouped['Toppings Premium'],
                sauces: additionsGrouped['Salsas'],
                adicionales: additionsGrouped['Adicionales'],
                iceCream: additionsGrouped['HELADOS']
              }
            }
          };
        })
      };
    }

    groupAdditionsByType(additions: DetailAdditional[]): Record<string, Ingredient[]> {
      const grouped: Record<string, Ingredient[]> = {
        'Toppings Clásicos': [],
        'Toppings Premium': [],
        'Salsas': [],
        'Adicionales': [],
        'HELADOS': []
      };
  
      for (const addition of additions) {
        const typeName = addition.ingredient.ingredientType.name;
        if (grouped[typeName]) {
          grouped[typeName].push(addition.ingredient);
        }
      }
  
      return grouped;
    }
  

    isLastElement(topping: Ingredient, array: Ingredient[]): boolean {
      return array.indexOf(topping) === array.length - 1;
    }

    updateOrderById(idOrder: number) {
      this.loadingOrder = true;
      this.orderService.updateOrderById(idOrder).subscribe(res => {
        this.notificationService.success('¡Pedido actualizado exitosamente!', 'Éxito');
        this.router.navigate(['/intranet/orders']);
      }, error => {
        this.loadingOrder = false;
        this.notificationService.error('Ha ocurrido un error al actualizar el pedido. Contacte a Tecnología.')
      });
    }

    cancelEmit(idOrder: number) {
      this.orderService.cancelOrder(idOrder).subscribe(res => {
        this.router.navigate(['/intranet/orders'])
        this.notificationService.info('Se ha cancelado la orden');
      })
    }
  }
