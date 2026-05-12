import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { ITicket } from '../models/iticket.interface';
import { ITicketRequest } from '../models/iticket-request.interface';

@Injectable({
  providedIn: 'root',
})
export class TicketApiService {

  private readonly http = inject(HttpClient);

  private readonly ticketsUrl = '/api/accounts/tickets';

  create(ticket: ITicketRequest): Observable<ITicket> {
    return of({ id: '1', ...ticket });
  }

  loadAll(): Observable<ITicket[]> {
    return this.http.get<ITicket[]>(this.ticketsUrl);
  }

}