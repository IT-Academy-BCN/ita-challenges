import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { ITicketRequest } from '../../models/iticket-request.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-ticket-page',
  imports: [ReactiveFormsModule],
  templateUrl: './create-ticket-page.html',
  styleUrl: './create-ticket-page.css',
})
export class CreateTicketPage {
  readonly ticketService = inject(TicketApiService);
  readonly fb = inject(FormBuilder);
  readonly router = inject(Router);

  readonly ticketMessage = signal<string>('');

  ticketForm = this.fb.group({
    title: [''],
    description: ['']
  });

  onSubmit() {
    this.ticketMessage.set('');

    const newTicket = this.ticketForm.value as ITicketRequest;

    this.ticketService.create(newTicket).subscribe({
      next: () => {this.goTickets();},
      error: (err) => {
        this.ticketMessage.set(this.getMessageForStatus(err.status));
      }
    });
  }

  goTickets() {
    this.router.navigate(['/tickets']);
  }

  private getMessageForStatus(status: number): string {
    switch (status) {
      case 400:
        return 'Dades del ticket incorrectes';
      case 403:
        return 'No tens permís per crear aquest tiquet';
      default:
        return "Error en crear el ticket";
    }
  }
}
