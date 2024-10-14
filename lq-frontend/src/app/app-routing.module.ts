import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { BaseComponent } from './core/layout/components/base/base.component';
import { AuthPublicGuard } from './core/authentication/guards/auth-public.guard';
import { AuthGuard } from './core/authentication/guards/auth.guard';

const routes: Routes = [
  {
    path: 'signin',
    loadChildren: () => import('./modules/public/signin/signin.module').then((m) => m.SigninModule),
    data: { preload: true },
    canActivate : [AuthPublicGuard]
  },
  {
    path: '',
    component: BaseComponent,
    children: [
      {
        path: '',
        redirectTo: '/intranet',
        pathMatch: 'full' 
      },
      {
        path:'intranet',
        loadChildren: () => import('./modules/intranet/intranet.module').then((m) => m.IntranetModule)
      }
    ],
    data: { preload: false },
    canActivate : [AuthGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: PreloadAllModules
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
