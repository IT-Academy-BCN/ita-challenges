import { Component } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { TICKETS_MOCK } from '../../models/tickets.mock';

@Component({
  selector: 'app-ticket-detail-page',
  imports: [],
  templateUrl: './ticket-detail-page.html',
  styleUrl: './ticket-detail-page.css',
})
export class TicketDetailPage {

  ticket: ITicket = TICKETS_MOCK[0];
  statusOptions = [
    { value: 'OPEN', label: 'Obert' },
    { value: 'IN_PROGRESS', label: 'En progrés' },
    { value: 'RESOLVED', label: 'Resolt' }
  ];

}
