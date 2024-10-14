import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home'
  },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule)
  },
  {
    path: 'orders',
    loadChildren: () => import('./order/order.module').then((m) => m.OrderModule)
  },
  {
    path: 'products',
    loadChildren: () => import('./products/products.module').then((m) => m.ProductsModule)
  },
  {
    path: 'users',
    loadChildren: () => import('./users/users.module').then((m) => m.UsersModule)
  },
  {
    path: 'toppings',
    loadChildren: () => import('./toppings/toppings.module').then((m) => m.ToppingsModule)
  },
  {
    path: 'historic-sale',
    loadChildren: () => import('./historicSale/historic-sale.module').then((m) => m.HistoricSaleModule)
  },
  {
    path: 'combos',
    loadChildren: () => import('./combos/combos.module').then((m) => m.CombosModule)
  },
  {
    path: 'sale',
    loadChildren: () => import('./sale/sale.module').then((m) => m.SaleModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IntranetRoutingModule { }
