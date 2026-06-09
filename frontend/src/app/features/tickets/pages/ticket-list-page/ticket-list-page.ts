import { Component, inject, signal } from '@angular/core';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { ITicket } from '../../models/iticket.interface';
import { Card } from "../../components/card/card";

@Component({
  selector: 'app-ticket-list-page',
  standalone: true,
  imports: [Card],
  templateUrl: './ticket-list-page.html',
  styleUrl: './ticket-list-page.css',
})
export class TicketListPage {
  private readonly ticketService = inject(TicketApiService);

  tickets = signal<ITicket[]>([]);

  ngOnInit(): void {
    this.ticketService.loadAll().subscribe((data) => {
      this.tickets.set(data);
    })
  }
}
