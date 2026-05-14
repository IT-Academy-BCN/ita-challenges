import { Component, inject, OnInit, signal } from '@angular/core';

import { TicketApiService } from '../data-access/ticket-api.service';
import { ITicket } from '../models/iticket.interface';

@Component({
  standalone: true,
  selector: 'app-tickets-page',
  template: `
    <h1>Tiquets</h1>

    @if (tickets().length === 0) {
      <p>No tickets found.</p>
    } @else {
      <ul>
        @for (ticket of tickets(); track ticket.id) {
          <li>
            <strong>{{ ticket.title }}</strong>
            <p>{{ ticket.description }}</p>
          </li>
        }
      </ul>
    }
  `,
})
export class TicketsPage implements OnInit {

  private readonly ticketApiService = inject(TicketApiService);

  tickets = signal<ITicket[]>([]);

  ngOnInit(): void {

    this.ticketApiService.loadAll().subscribe({
      next: (tickets) => {
        this.tickets.set(tickets);
      },
    });
  }
}
