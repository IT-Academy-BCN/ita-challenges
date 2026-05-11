import { Routes } from '@angular/router';
import { TicketsPage } from './pages/tickets-page.component';
import { CreateTicketPage } from './pages/create-ticket-page/create-ticket-page';

export const TICKETS_ROUTES: Routes = [
  {
    path: '',
    component: TicketsPage,
  },
  {
    path: 'create-ticket',
    component: CreateTicketPage,
  },  
];
