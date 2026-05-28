import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TicketDetailPage } from './ticket-detail-page';
import { TICKETS_MOCK } from '../../models/tickets.mock';
import { By } from '@angular/platform-browser';
import { TicketService } from '../../ticket.service';
import { of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { TicketApiService } from '../../data-access/ticket-api.service';

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
          provide: TicketApiService,
          useValue: { update: () => of(mockTicket) }
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

  it('should preselect the status from the ticket', () => {
    expect(component.ticketForm.value.status).toBe(mockTicket.status);
  });

  it('should call update on submit', () => {
    const ticketApiService = TestBed.inject(TicketApiService);
    vi.spyOn(ticketApiService, 'update').mockReturnValue(of(mockTicket));

    component.onSubmit();

    expect(ticketApiService.update).toHaveBeenCalledWith(mockTicket.id, {
      status: mockTicket.status
    });
  })

  it('should render the comment textarea', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const textareaElement = compiled.querySelector('textarea');

    expect(textareaElement).toBeTruthy();
    expect(textareaElement?.getAttribute('id')).toBe('comment');
    expect(textareaElement?.getAttribute('rows')).toBe('10');
  });

 it('should NOT render the comment section if ticket.comment is null or undefined', () => {
     const ticketService = TestBed.inject(TicketService);
     vi.spyOn(ticketService, 'getById').mockReturnValue(of({ ...TICKETS_MOCK[0], comment: null as any }));

     const freshFixture = TestBed.createComponent(TicketDetailPage);
     freshFixture.detectChanges();

     const commentSection = freshFixture.debugElement.query(By.css('.ticket-comment-section'));
     expect(commentSection).toBeNull();
   });

   it('should render the comment section if ticket.comment has text', () => {
     const testComment = 'This is a test comment for testing purposes.';
     const ticketService = TestBed.inject(TicketService);
     vi.spyOn(ticketService, 'getById').mockReturnValue(of({ ...TICKETS_MOCK[0], comment: testComment }));

     const freshFixture = TestBed.createComponent(TicketDetailPage);
     freshFixture.detectChanges();

     const commentSection = freshFixture.debugElement.query(By.css('.ticket-comment-section'));
     expect(commentSection).toBeTruthy();
     expect(commentSection.nativeElement.textContent).toContain(testComment);
   });
 });

