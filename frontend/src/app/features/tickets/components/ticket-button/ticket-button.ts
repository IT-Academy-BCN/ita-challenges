import { Component, input } from '@angular/core';

@Component({
  selector: 'app-ticket-button',
  imports: [],
  templateUrl: './ticket-button.html',
  styleUrl: './ticket-button.css',
})
export class TicketButton {
  label = input<string>('Button placeholder');
}
