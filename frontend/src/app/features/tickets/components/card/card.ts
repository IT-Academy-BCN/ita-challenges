import { Component, input } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { RouterLink } from '@angular/router';
import { Button } from '../button/button';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-card',
  imports: [RouterLink, Button, DatePipe],
  templateUrl: './card.html',
  styleUrl: './card.css',
})
export class Card {
  ticket = input.required<ITicket>();
}