import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';

import {ITicket} from '../models/iticket.interface';
import {ITicketRequest} from '../models/iticket-request.interface';
import {TICKETS_MOCK} from '../models/tickets.mock';
import { AssignableUser } from '../models/assignable-user.interface';

@Injectable({
  providedIn: 'root',
})
export class TicketApiService {
  private readonly http = inject(HttpClient);
  private readonly ticketsUrl = '/api/account/tickets';

  create(ticket: ITicketRequest): Observable<ITicket> {
    return this.http.post<ITicket>(this.ticketsUrl, ticket, { withCredentials: true }).pipe(
      catchError((error: HttpErrorResponse) => {
        const message = typeof error.error === 'string' ? error.error : error.error?.message || error.message;
        const errorWithMessage = { ...error, userMessage: message };
        return throwError(() => errorWithMessage);
      })
    );
  }

  loadAll(): Observable<ITicket[]> {
    return this.http.get<ITicket[]>(this.ticketsUrl).pipe(
      catchError((error: HttpErrorResponse) => {
        return of(TICKETS_MOCK);
      })
    );
  }

  update(id: string, data: Partial<ITicket>): Observable<ITicket> {
    return this.http.patch<ITicket>(`${this.ticketsUrl}/${id}`, data);
  }
  getTicketAssignableUsers(): Observable<AssignableUser[]> {
    return this.http.get<AssignableUser[]>('/api/account/users/mentors').pipe(
      catchError((error: HttpErrorResponse) => {
        const message = typeof error.error === 'string' ? error.error : error.error?.message || error.message;
        const errorWithMessage = { ...error, userMessage: message };
        return throwError(() => errorWithMessage);
      })
    )
  }
}
