import { Routes } from '@angular/router';
import { AdminPage } from './pages/admin-page.component';
import { AssignRolePage } from './pages/assign-role-page/assign-role-page';


export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminPage,
  },
  {
    path: 'assign-role',
    component: AssignRolePage,
  }
];
