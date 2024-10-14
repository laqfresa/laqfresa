import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ToppingsRoutingModule } from './toppings-routing.module';
import { ToppingsComponent } from './pages/toppings/toppings.component';
import { TableToppingsComponent } from './components/table-toppings/table-toppings.component';
import { ModalEditIngredientComponent } from './components/modal-edit-ingredient/modal-edit-ingredient.component';
import { ModalEditTypeIngredientComponent } from './components/modal-edit-type-ingredient/modal-edit-type-ingredient.component';
import { ModalCreatedIngredientComponent } from './components/modal-created-ingredient/modal-created-ingredient.component';
import { ModalCreatedTypeIngredientComponent } from './components/modal-created-type-ingredient/modal-created-type-ingredient.component';
@NgModule({
  declarations: [
    ToppingsComponent,
    TableToppingsComponent,
    ModalEditIngredientComponent,
    ModalEditTypeIngredientComponent,
    ModalCreatedIngredientComponent,
    ModalCreatedTypeIngredientComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ToppingsRoutingModule,  
  ],
  providers: [],
})
export class ToppingsModule { }