import { DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { registerLocaleData, HashLocationStrategy, LocationStrategy } from '@angular/common';
import localeEsCO from '@angular/common/locales/es-CO'; // Importar locale
import {HttpClientModule} from "@angular/common/http";
import {es_ES, NZ_DATE_LOCALE, NZ_I18N} from 'ng-zorro-antd/i18n';
import {es as DateES} from 'date-fns/locale';

registerLocaleData(localeEsCO, 'es-CO');

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: es_ES },
    { provide: LOCALE_ID, useValue: 'es-CO' },
    { provide: NZ_DATE_LOCALE, useValue: DateES },
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'COP' },
    { provide: LocationStrategy, useClass:HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
