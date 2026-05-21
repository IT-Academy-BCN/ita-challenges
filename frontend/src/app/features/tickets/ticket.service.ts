import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { IChallenge } from '../challenges/models/ichallenge.interface';
import { TicketApiService } from './data-access/ticket-api.service';

@Injectable({
  providedIn: 'root',
})
export class Ticket {

  private readonly ticketApiService = inject(TicketApiService)

  getById(id: string): Observable<IChallenge | undefined> {
      return this.ticketApiService.loadAll().pipe(
        map((tickets) => tickets.find(((ticket) => ticket.id === id)))
      )
    }

}
