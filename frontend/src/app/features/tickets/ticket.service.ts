import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { TicketApiService } from './data-access/ticket-api.service';
import { ITicket } from './models/iticket.interface';
import { AssignableUser } from './models/assignable-user.interface';

@Injectable({
  providedIn: 'root',
})
export class TicketService {

  private readonly ticketApiService = inject(TicketApiService)

  getById(id: string): Observable<ITicket | undefined> {
      return this.ticketApiService.loadAll().pipe(
        map((tickets) => tickets.find(((ticket) => ticket.id === id)))
      )
    }
  
  getTicketAssignableUsers(): Observable<AssignableUser[]> {
    return this.ticketApiService.getTicketAssignableUsers();
  }
  
  updateAssignedMentor(ticketId: string, mentorAssignedId: string): Observable<ITicket> {
    return this.ticketApiService.updateAssignedMentor(ticketId, {
      mentorAssignedId: mentorAssignedId
    });
  }
}
