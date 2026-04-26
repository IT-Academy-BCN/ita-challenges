import { Routes } from '@angular/router';
import { ProfilePageComponent } from './pages/profile-page/profile-page';
import { authGuard } from '../../core/guards/auth-guard';

export const PROFILE_ROUTES: Routes = [
  {
    path: '',
    component: ProfilePageComponent,
    canActivate: [authGuard],
  },
];