import { Component, inject, signal } from '@angular/core';
import { ITicket } from '../../models/iticket.interface';
import { TicketService } from '../../ticket.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ticket-detail-page',
  imports: [],
  templateUrl: './ticket-detail-page.html',
  styleUrl: './ticket-detail-page.css',
})
export class TicketDetailPage {

  private readonly ticketService = inject(TicketService)
  private readonly route = inject(ActivatedRoute)
  ticket = signal<ITicket | undefined>(undefined)


  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.ticketService.getById(id).subscribe((selectedChallenge) => {
      this.ticket.set(selectedChallenge);
    });
  }

  statusOptions = [
    { value: 'OPEN', label: 'Obert' },
    { value: 'IN_PROGRESS', label: 'En progrés' },
    { value: 'RESOLVED', label: 'Resolt' }
  ];

}
