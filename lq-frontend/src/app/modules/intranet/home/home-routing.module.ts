import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeIntranetComponent } from './pages/home-intranet/home-intranet.component';

const routes: Routes = [
  {
    path: '',
    component: HomeIntranetComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
