import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { provideHttpClient } from '@angular/common/http';
import { TicketApiService } from './ticket-api.service';
import { ITicket } from '../models/iticket.interface';
import { TICKETS_MOCK } from '../models/tickets.mock';

describe('TicketApiService', () => {

  let service: TicketApiService;
  let httpMock: HttpTestingController;

  const API_URL = '/api/accounts/tickets';
  const MOCK_TICKETS: ITicket[] = [
    {
      id: '1',
      userId: 'user-1',
      title: 'Login issue',
      description: 'Unable to login with GitHub account',
    },
    {
      id: '2',
      userId: 'user-2',
      title: 'UI bug',
      description: 'Sidebar overlaps content on mobile devices',
    },
    {
      id: '3',
      userId: 'user-3',
      title: 'Feature request',
      description: 'Add ticket filtering by status',
    },
  ];
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

  it('should GET from the correct URL', () => {
    service.loadAll().subscribe();
    const req = httpMock.expectOne(API_URL);
    expect(req.request.url).toBe(API_URL);
    req.flush(MOCK_TICKETS);
  });

  it('should use GET method', () => {
    service.loadAll().subscribe();
    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('GET');
    req.flush(MOCK_TICKETS);
  });

  it('should return tickets from API', () => {
    service.loadAll().subscribe((tickets) => {
      expect(tickets).toEqual(MOCK_TICKETS);
    });

    const req = httpMock.expectOne(API_URL);
    req.flush(MOCK_TICKETS);
  });

  it('should return mock tickets on API error', () => {
    service.loadAll().subscribe((tickets) => {
      expect(tickets).toEqual(TICKETS_MOCK);
    });

    const req = httpMock.expectOne(API_URL);
    req.flush('Error', {
      status: 500,
      statusText: 'Server Error',
    });
  });
});