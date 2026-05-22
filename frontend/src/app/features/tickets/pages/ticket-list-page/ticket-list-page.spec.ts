import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { TicketListPage } from './ticket-list-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { TICKETS_MOCK } from '../../models/tickets.mock';

describe('TicketListPage', () => {
  let component: TicketListPage;
  let fixture: ComponentFixture<TicketListPage>;
  let mockTicketApiService: Partial<TicketApiService>;

  beforeEach(async () => {
    TestBed.resetTestingModule();
    mockTicketApiService = { loadAll: vi.fn().mockReturnValue(of(TICKETS_MOCK)) };

    await TestBed.configureTestingModule({
      imports: [TicketListPage],
      providers: [
        { provide: TicketApiService, useValue: mockTicketApiService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TicketListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should call loadAll on init', () => {
    expect(mockTicketApiService.loadAll).toHaveBeenCalled();
  });

  it('should load tickets into component state', () => {
    expect(component.tickets()).toEqual(TICKETS_MOCK);
  });

  it('should render all tickets in the template', () => {
    const listItems = fixture.nativeElement.querySelectorAll('li');
    expect(listItems.length).toBe(TICKETS_MOCK.length);
  });

  it('should display ticket title and description', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const firstTicket = TICKETS_MOCK[0];

    expect(compiled.textContent).toContain(firstTicket.title);
    expect(compiled.textContent).toContain(firstTicket.description);
  });
});
