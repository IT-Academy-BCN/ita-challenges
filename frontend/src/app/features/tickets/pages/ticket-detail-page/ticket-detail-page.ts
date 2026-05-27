import { Component, inject, signal } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { TicketService } from '../../ticket.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { TicketApiService } from '../../data-access/ticket-api.service';

@Component({
  selector: 'app-ticket-detail-page',
  imports: [ReactiveFormsModule],
  templateUrl: './ticket-detail-page.html',
  styleUrl: './ticket-detail-page.css',
})
export class TicketDetailPage {

  private readonly ticketService = inject(TicketService)
  private readonly ticketApiService = inject(TicketApiService)
  private readonly route = inject(ActivatedRoute)
  private readonly fb = inject(FormBuilder)
  ticket = signal<ITicket | undefined>(undefined)


  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.ticketService.getById(id).subscribe((selectedTicket) => {
      this.ticket.set(selectedTicket);
      this.ticketForm.patchValue({ status: selectedTicket?.status });
    });
  }

  statusOptions = [
    { value: 'OPEN', label: 'Obert' },
    { value: 'IN_PROGRESS', label: 'En progrés' },
    { value: 'RESOLVED', label: 'Resolt' }
  ];

  ticketForm = this.fb.group({
      status: ['' as ITicket['status']],
      comment: ''
    })

  onSubmit() : void {
    const currentTicket = this.ticket();

    if (this.ticketForm.valid && currentTicket?.id) {
      const ticketUpdate = {
        status: this.ticketForm.value.status ?? undefined,
        comment: this.ticketForm.value.comment || null
        };

      this.ticketApiService.update(currentTicket.id, ticketUpdate).subscribe({
      next: () => {
        alert('Canvi guardat!');
      }
    });
    }
  }

}
