import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { TicketApiService } from './ticket-api.service';
import { TICKETS_MOCK } from '../models/tickets.mock';

describe('TicketApiService', () => {
  let service: TicketApiService;
  let httpMock: HttpTestingController;

  const API_URL = '/api/accounts/tickets';

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

  it('should return tickets from API', () => {
    service.loadAll().subscribe((tickets) => {
      expect(tickets).toEqual(TICKETS_MOCK);
    });

    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('GET');
    req.flush(TICKETS_MOCK);
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

  it('should POST to the correct URL', () => {
    const ticket = {
      userId: 'u-1',
      title: 'Issue title',
      description: 'Issue description',
    };

    service.create(ticket).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('POST');
  });

  it('should send the correct payload', () => {
    const ticket = {
      userId: 'u-1',
      title: 'Issue title',
      description: 'Issue description',
    };

    service.create(ticket).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.body).toEqual(ticket);
    req.flush({ id: '1', ...ticket });
  });

  it('should return backend data on success', () => {
    const ticket = {
      userId: 'u-1',
      title: 'Issue title',
      description: 'Issue description',
    };

    const response = { id: '1', ...ticket };
    let result: any;

    service.create(ticket).subscribe((value) => {
      result = value;
    });

    httpMock.expectOne(API_URL).flush(response);

    expect(result).toEqual(response);
  });
});
