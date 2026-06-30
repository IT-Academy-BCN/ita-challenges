import { Component, inject, input, linkedSignal } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { RouterLink } from '@angular/router';
import { TicketButton } from '../ticket-button/ticket-button';
import { DatePipe } from '@angular/common';
import { TicketService } from '../../ticket.service';
import { AssignableUser } from '../../models/assignable-user.interface';

@Component({
  selector: 'app-ticket-card',
  imports: [RouterLink, TicketButton, DatePipe],
  templateUrl: './ticket-card.html',
  styleUrl: './ticket-card.css',
})
export class TicketCard {
  private readonly ticketService = inject(TicketService);
  ticket = input.required<ITicket>();
  assignableUsers = input.required<AssignableUser[]>();

  selectedMentorId = linkedSignal<string | null>(() => this.ticket().mentorAssignedId ?? null);

  onSelectChange(event: Event) {
    const value = (event.target as HTMLSelectElement).value || null;
    this.selectedMentorId.set(value);
    this.ticketService.updateAssignedMentor(this.ticket().id, value ?? '').subscribe({
      error: () => this.selectedMentorId.set(this.ticket().mentorAssignedId ?? null)
    });
  }
}