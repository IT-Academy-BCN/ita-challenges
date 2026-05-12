import { Routes } from '@angular/router';
import { TicketListPage } from './pages/ticket-list-page/ticket-list-page';

export const TICKETS_ROUTES: Routes = [
  {
    path: '',
    component: TicketListPage,
  }
];
