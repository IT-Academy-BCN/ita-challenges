import { Injectable } from '@angular/core';
import { ITicket } from '../models/iticket.interface';
import { Observable, of } from 'rxjs';
import { TICKETS_MOCK } from '../models/tickets.mock';
import { ITicketRequest } from '../models/iticket-request.interface';

@Injectable({
  providedIn: 'root',
})
export class TicketApiService {

  create(ticket: ITicketRequest): Observable<ITicket> {
    return of( { id: '1', ...ticket} );
  }

  loadAll(): Observable<ITicket[]> {
    return of( TICKETS_MOCK );
  }
}
