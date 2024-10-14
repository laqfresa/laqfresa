import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeIntranetComponent } from './pages/home-intranet/home-intranet.component';
import { HomeRoutingModule } from './home-routing.module'
import { SharedModule } from 'src/app/shared/shared.module';
import { TabToppingsComponent } from './components/tab-toppings/tab-toppings.component';
import { ModalResumeOrderComponent } from './components/modal-resume-order/modal-resume-order.component';
import { TabCombosComponent } from './components/tab-combos/tab-combos.component';
import { ModalEditProductComponent } from './components/modal-edit-product/modal-edit-product.component';



@NgModule({
  declarations: [
    HomeIntranetComponent,
    TabToppingsComponent,
    ModalResumeOrderComponent,
    TabCombosComponent,
    ModalEditProductComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule
  ]
})
export class HomeModule { }
