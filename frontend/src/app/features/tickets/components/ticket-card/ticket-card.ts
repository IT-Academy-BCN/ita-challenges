import { Component, input } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { RouterLink } from '@angular/router';
import { TicketButton } from '../ticket-button/ticket-button';
import { DatePipe } from '@angular/common';
import { AssignableUser } from '../../models/assignable-user.interface';

@Component({
  selector: 'app-ticket-card',
  imports: [RouterLink, TicketButton, DatePipe],
  templateUrl: './ticket-card.html',
  styleUrl: './ticket-card.css',
})
export class TicketCard {
  ticket = input.required<ITicket>();
  assignableUsers = input.required<AssignableUser[]>();
}