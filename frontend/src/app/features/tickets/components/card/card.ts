import { Component, input } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card',
  imports: [RouterLink],
  templateUrl: './card.html',
  styleUrl: './card.css',
})
export class Card {
  ticket = input.required<ITicket>();
}