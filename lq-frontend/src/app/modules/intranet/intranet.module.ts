import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IntranetRoutingModule } from './intranet-routing.module'
import { NgZorroModule } from 'src/app/shared/modules/ng-zorro.module';
import { HomeModule } from './home/home.module';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [],
  imports: [
    NgZorroModule,
    CommonModule,
    IntranetRoutingModule,
    HomeModule,
    SharedModule
  ]
})
export class IntranetModule { }
