import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
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
  private readonly cdr = inject(ChangeDetectorRef);
  readonly ticketService = inject(TicketApiService)
  readonly fb = inject(FormBuilder)
  readonly router = inject(Router)

  errorMessage: string | null = null;

  ticketForm = this.fb.group({
    title: [''],
    description: ['']
  })

  onSubmit() {
    this.errorMessage = null;

    const newTicket = this.ticketForm.value as ITicketRequest

    this.ticketService.create(newTicket).subscribe({
      next: () => {this.goTickets();},
      error: (err) => {
        this.errorMessage = 'Error al crear el ticket: ' + err.error;
        this.cdr.detectChanges();
      }
    });
  }

  goTickets() {
    this.router.navigate(['/tickets']);
  }    
}
