import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SigninRoutingModule } from './signin-routing.module';
import { FormSigninComponent } from './components/form-signin/form-signin.component'
import { SigninComponent } from './pages/signin/signin.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    SigninComponent,
    FormSigninComponent
  ],
  exports: [
    SigninComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SigninRoutingModule,
    SharedModule,
  ]
})
export class SigninModule { }
