import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketListPage } from './ticket-list-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { TICKETS_MOCK } from '../../models/tickets.mock';

describe('TicketListPage', () => {
  let component: TicketListPage;
  let fixture: ComponentFixture<TicketListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketListPage],
      providers: [TicketApiService],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketListPage);
    component = fixture.componentInstance;

    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load tickets on init', () => {
    expect(component.tickets()).toEqual(TICKETS_MOCK);
  });

  it('should render all tickets in template', () => {
    const listItems = fixture.nativeElement.querySelectorAll('li');

    expect(listItems.length).toBe(TICKETS_MOCK.length);
  });

  it('should render ticket title and description', () => {
    const compiled = fixture.nativeElement as HTMLElement;

    expect(compiled.textContent).toContain(TICKETS_MOCK[0].title);
    expect(compiled.textContent).toContain(TICKETS_MOCK[0].description);
  });
});