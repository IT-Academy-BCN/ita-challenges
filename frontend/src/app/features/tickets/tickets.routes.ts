import { Routes } from '@angular/router';
import { TicketListPage } from './pages/ticket-list-page/ticket-list-page';
import { CreateTicketPage } from './pages/create-ticket-page/create-ticket-page';
import { TicketDetailPage } from './pages/ticket-detail-page/ticket-detail-page';

export const TICKETS_ROUTES: Routes = [
  {
    path: '',
    component: TicketListPage,
  },
  {
    path: 'create-ticket',
    component: CreateTicketPage,
  },
  {
    path: ':id',
    component: TicketDetailPage,
    },
];
