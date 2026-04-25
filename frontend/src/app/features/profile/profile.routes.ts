import { Routes } from '@angular/router';
import { ProfilePage } from './pages/profile-page.component';
import { authGuard } from '../../core/guards/auth-guard';

export const PROFILE_ROUTES: Routes = [
  {
    path: '',
    component: ProfilePage,
    canActivate: [authGuard],
  },
];
