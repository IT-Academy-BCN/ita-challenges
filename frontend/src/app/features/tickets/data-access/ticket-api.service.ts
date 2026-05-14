import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';

import { ITicket } from '../models/iticket.interface';
import { ITicketRequest } from '../models/iticket-request.interface';
import { TICKETS_MOCK } from '../models/tickets.mock';

@Injectable({
  providedIn: 'root',
})
export class TicketApiService {
  private readonly http = inject(HttpClient);
  private readonly ticketsUrl = '/api/accounts/tickets';

  create(ticket: ITicketRequest): Observable<ITicket> {
    return this.http.post<ITicket>(this.ticketsUrl, ticket).pipe(
      catchError(() => of({ id: '1', ...ticket } as ITicket))
    );
  }

  loadAll(): Observable<ITicket[]> {
    return this.http.get<ITicket[]>(this.ticketsUrl).pipe(
      catchError((error: HttpErrorResponse) => {
        return of(TICKETS_MOCK);
      })
    );
  }
}
