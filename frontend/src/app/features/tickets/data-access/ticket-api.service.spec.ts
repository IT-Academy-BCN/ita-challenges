import { TestBed } from '@angular/core/testing';

import { TicketApiService } from './ticket-api.service';

describe('TicketApiService', () => {
  let service: TicketApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
