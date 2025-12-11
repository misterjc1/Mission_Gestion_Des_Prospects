import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./components/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./components/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'prospects',
    loadComponent: () => import('./components/prospects/prospect-list/prospect-list.component').then(m => m.ProspectListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'prospects/new',
    loadComponent: () => import('./components/prospects/prospect-form/prospect-form.component').then(m => m.ProspectFormComponent),
    canActivate: [authGuard]
  },
  {
    path: 'prospects/:id',
    loadComponent: () => import('./components/prospects/prospect-detail/prospect-detail.component').then(m => m.ProspectDetailComponent),
    canActivate: [authGuard]
  },
  {
    path: 'prospects/:id/edit',
    loadComponent: () => import('./components/prospects/prospect-form/prospect-form.component').then(m => m.ProspectFormComponent),
    canActivate: [authGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./components/users/user-profile/user-profile.component').then(m => m.UserProfileComponent),
    canActivate: [authGuard]
  },
  {
    path: 'users',
    loadComponent: () => import('./components/users/user-list/user-list.component').then(m => m.UserListComponent),
    canActivate: [authGuard, adminGuard]
  },
  {
    path: '**',
    loadComponent: () => import('./components/shared/not-found/not-found.component').then(m => m.NotFoundComponent)
  }
];
