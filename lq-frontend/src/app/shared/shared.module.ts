import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgZorroModule } from './modules/ng-zorro.module';
import { IconsProviderModule } from './modules/icons-provider.module';
import { LayoutModule } from '../core/layout/layout.module';
import { ComponentsModule } from './components/components.module';
import { IConfig, NgxMaskModule} from 'ngx-mask';

export const options: Partial <null | IConfig> | (() => Partial<IConfig>) = null;

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NgxMaskModule.forRoot()
  ],
  exports: [
    NgZorroModule,
    IconsProviderModule,
    LayoutModule,
    ComponentsModule
  ]
})
export class SharedModule { }
