import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToppingsComponent } from './pages/toppings/toppings.component';

const routes: Routes = [
  {
    path: '',
    component: ToppingsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ToppingsRoutingModule { }