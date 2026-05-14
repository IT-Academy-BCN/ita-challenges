import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { ITicketRequest } from '../../models/iticket-request.interface';

@Component({
  selector: 'app-create-ticket-page',
  imports: [ReactiveFormsModule],
  templateUrl: './create-ticket-page.html',
  styleUrl: './create-ticket-page.css',
})
export class CreateTicketPage {
  readonly ticketService = inject(TicketApiService)
  readonly fb = inject(FormBuilder)

  ticketForm = this.fb.group({
    userId: [''],
    title: [''],
    description: ['']
  })

  onSubmit() {
    const newTicket = this.ticketForm.value as ITicketRequest

    this.ticketService.create(newTicket).subscribe({
      next: () => {this.ticketForm.reset();}
    });
  }
}
