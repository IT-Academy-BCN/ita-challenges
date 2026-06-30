import { Component, inject, signal } from '@angular/core';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { ITicket } from '../../models/iticket.interface';
import { TicketCard } from "../../components/ticket-card/ticket-card";
import { AssignableUser } from '../../models/assignable-user.interface';
import { TicketService } from '../../ticket.service';

@Component({
  selector: 'app-ticket-list-page',
  standalone: true,
  imports: [TicketCard],
  templateUrl: './ticket-list-page.html',
  styleUrl: './ticket-list-page.css',
})
export class TicketListPage {
  private readonly ticketApiService = inject(TicketApiService);
  private readonly ticketService = inject(TicketService);

  tickets = signal<ITicket[]>([]);
  assignableUsers = signal<AssignableUser[]>([]);

  ngOnInit(): void {
    this.ticketApiService.loadAll().subscribe((data) => {
      this.tickets.set(data);
    });
    this.ticketService.getTicketAssignableUsers().subscribe((data) => {
      this.assignableUsers.set(data)
    })
  }
}