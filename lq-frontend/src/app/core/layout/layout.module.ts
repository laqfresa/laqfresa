import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NgZorroModule } from '../../shared/modules/ng-zorro.module';
import { BaseComponent } from './components/base/base.component';
import { SideMenu } from './components/side-menu/side-menu.component';
import { MenuFooter } from './components/menu-footer/menu-footer.component'

@NgModule({
  declarations: [
    BaseComponent,
    SideMenu,
    MenuFooter
  ],
  imports: [
    CommonModule,
    NgZorroModule,
    RouterModule
  ]
})
export class LayoutModule { }
