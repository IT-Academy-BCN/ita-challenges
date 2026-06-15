import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { TicketApiService } from './ticket-api.service';
import { TICKETS_MOCK } from '../models/tickets.mock';
import { ITicket } from '../models/iticket.interface';
import { TicketStatus } from '../models/status.enum';

describe('TicketApiService', () => {
  let service: TicketApiService;
  let httpMock: HttpTestingController;

  const mockTicket = {
    title: 'Issue title',
    description: 'Issue description',
  };

  const mockTicketResponse = { id: '1', userId: 'u-1', ...mockTicket };


  const API_URL = '/api/account/tickets';

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

    service.create(mockTicket).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('POST');
    req.flush(mockTicketResponse);
  });

  it('should send the correct payload', () => {
    service.create(mockTicket).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.body).toEqual(mockTicket);
    req.flush(mockTicketResponse);
  });

  it('should return backend data on success', () => {
    let result;

    service.create(mockTicket).subscribe((value) => {
      result = value;
    });

    httpMock.expectOne(API_URL).flush(mockTicketResponse);

    expect(result).toEqual(mockTicketResponse);
  });

  it('should throw error on create error', () => {
    let error: any;

    service.create(mockTicket).subscribe({
      next: () => {},
      error: (err) => {
        error = err;
      }
    });

    const req = httpMock.expectOne(API_URL);
    req.flush('Error', {
      status: 500,
      statusText: 'Server Error',
    });

    expect(error.status).toBe(500);
  });

  it('should call PATCH with correct URL and body', () => {
  const id = '123';
  const partialData: Partial<ITicket> = { status: TicketStatus.RESOLVED };

  service.update(id, partialData).subscribe();

  const req = httpMock.expectOne(`${API_URL}/${id}`);
  expect(req.request.method).toBe('PATCH');
  expect(req.request.body).toEqual(partialData);
  req.flush({});
});
});
