import { Component, input } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-ticket-card',
  imports: [RouterLink],
  templateUrl: './ticket-card.html',
  styleUrl: './ticket-card.css',
})
export class TicketCard {
  ticket = input.required<ITicket>();
}