import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TicketDetailPage } from './ticket-detail-page';
import { TICKETS_MOCK } from '../../models/tickets.mock';
import { TicketService } from '../../ticket.service';
import { of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

describe('TicketDetailPage', () => {
  let component: TicketDetailPage;
  let fixture: ComponentFixture<TicketDetailPage>;
  const mockTicket = TICKETS_MOCK[0];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketDetailPage],
      providers: [
        {
          provide: TicketService,
          useValue: { getById: () => of(mockTicket) }
        },
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: { get: () => mockTicket.id } } }
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketDetailPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render ticket details in the HTML', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const titleElement = compiled.querySelector('h1');
    const paragraphs = compiled.querySelectorAll('p');

    expect(titleElement?.textContent).toContain(mockTicket.title);
    expect(paragraphs[0]?.textContent).toContain(mockTicket.userId);
    expect(paragraphs[1]?.textContent).toContain(mockTicket.description);
  });

  it('should render the status select with 3 options', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const selectElement = compiled.querySelector('select');
    const options = compiled.querySelectorAll('select option');

    expect(selectElement).toBeTruthy();
    expect(options.length).toBe(4);
  });

  it('should render the comment textarea', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const textareaElement = compiled.querySelector('textarea');
    expect(textareaElement).toBeTruthy();
    expect(textareaElement?.getAttribute('id')).toBe('comment');
    expect(textareaElement?.getAttribute('rows')).toBe('10');
  });
});
