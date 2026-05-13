import { Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/components/app-layout.component';
import { NotFoundPage } from './shared/pages/not-found-page';

export const appRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'challenges',
  },
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      {
        path: 'auth',
        loadChildren: () =>
          import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES),
      },
      {
        path: 'challenges',
        loadChildren: () =>
          import('./features/challenges/challenges.routes').then(m => m.CHALLENGES_ROUTES),
      },
      {
        path: 'solutions',
        loadChildren: () =>
          import('./features/solutions/solutions.routes').then(m => m.SOLUTIONS_ROUTES),
      },
      {
        path: 'profile',
        loadChildren: () =>
          import('./features/profile/profile.routes').then(m => m.PROFILE_ROUTES),
      },
      {
        path: 'admin',
        loadChildren: () =>
          import('./features/admin/admin.routes').then(m => m.ADMIN_ROUTES),
      },
      {
        path: 'tickets',
        loadChildren: () =>
          import('./features/tickets/tickets.routes').then(m => m.TICKETS_ROUTES),
      },      {
        path: '**',
        component: NotFoundPage,
      },
    ],
  },
];
