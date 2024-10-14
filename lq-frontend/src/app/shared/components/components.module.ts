import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgZorroModule } from '../modules/ng-zorro.module'
import { IconsProviderModule } from '../modules/icons-provider.module'
import { LayoutModule } from '../../core/layout/layout.module';
import { LoadingBoxComponent } from '../components/loading-box/loading-box.component'

@NgModule({
  declarations: [
    LoadingBoxComponent
  ],
  imports: [
    CommonModule,
    NgZorroModule,
    IconsProviderModule
  ],
  exports: [
    NgZorroModule,
    IconsProviderModule,
    LayoutModule,
    LoadingBoxComponent
  ]
})
export class ComponentsModule { }
