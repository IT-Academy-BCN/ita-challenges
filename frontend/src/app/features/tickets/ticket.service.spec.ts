import { TestBed } from '@angular/core/testing';
import { TicketService } from './ticket.service';
import { TICKETS_MOCK } from './models/tickets.mock';
import { firstValueFrom, of } from 'rxjs';
import { TicketApiService } from './data-access/ticket-api.service';

describe('Ticket', () => {
  let service: TicketService;
  let mockTicketApiService: any;

  beforeEach(() => {
    mockTicketApiService = {
      loadAll: vi.fn().mockReturnValue(of(TICKETS_MOCK))
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: TicketApiService, useValue: mockTicketApiService }
      ]
    });
    service = TestBed.inject(TicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return ticket by id', async () => {
    const ticket = await firstValueFrom(service.getById('1'));
    expect(ticket).toEqual(TICKETS_MOCK[0]);
  });

  it('should return undefined when ticket not found', async () => {
    const ticket = await firstValueFrom(service.getById('999'));
    expect(ticket).toBeUndefined();
  });
});
