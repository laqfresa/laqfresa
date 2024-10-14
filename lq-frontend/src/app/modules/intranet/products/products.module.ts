import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProductsRoutingModule } from './products-routing.module';
import { TableProductsComponent } from './components/table-products/table-products.component';
import { ProductsComponent } from './pages/products/products.component';
import { ModalInfoProductComponent } from './components/modal-info-product/modal-info-product.component';
import { ModalEditProductComponent } from './components/modal-edit-product/modal-edit-product.component';
import { ModalAddProductComponent } from './components/modal-add-product/modal-add-product.component';
@NgModule({
  declarations: [
    ProductsComponent,
    TableProductsComponent,
    ModalInfoProductComponent,
    ModalEditProductComponent,
    ModalAddProductComponent,

  ],
  imports: [
    CommonModule,
    SharedModule,
    ProductsRoutingModule,  
  ],
  providers: [],
})
export class ProductsModule { }