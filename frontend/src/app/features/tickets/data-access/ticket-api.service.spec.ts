import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { provideHttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { TicketApiService } from './ticket-api.service';
import { ITicket } from '../models/iticket.interface';

const MOCK_TICKETS: ITicket[] = [
  {
    id: '1',
    userId: 'user-1',
    title: 'Test ticket',
    description: 'Test description',
  },
];
describe('TicketApiService', () => {
  let service: TicketApiService;
  let httpMock: HttpTestingController;
  beforeEach(() => {

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(TicketApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });
  afterEach(() => {
    httpMock.verify();
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should load all tickets from API', async () => {
    const promise = firstValueFrom(service.loadAll());
    const req = httpMock.expectOne('/api/accounts/tickets');
    expect(req.request.method).toBe('GET');
    req.flush(MOCK_TICKETS);
    const result = await promise;
    expect(result).toEqual(MOCK_TICKETS);
  });
});