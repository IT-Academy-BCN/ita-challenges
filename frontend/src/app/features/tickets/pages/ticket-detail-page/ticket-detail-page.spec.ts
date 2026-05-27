import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TicketDetailPage } from './ticket-detail-page';
import { TICKETS_MOCK } from '../../models/tickets.mock';
import { By } from '@angular/platform-browser';

describe('TicketDetailPage', () => {
  let component: TicketDetailPage;
  let fixture: ComponentFixture<TicketDetailPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketDetailPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketDetailPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render ticket details in the HTML', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const titleElement = compiled.querySelector('h1');
    const paragraphs = compiled.querySelectorAll('p');

    expect(titleElement?.textContent).toContain(TICKETS_MOCK[0].title);
    expect(paragraphs[0]?.textContent).toContain(TICKETS_MOCK[0].userId);
    expect(paragraphs[1]?.textContent).toContain(TICKETS_MOCK[0].description);
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

 it('should NOT render the comment section if ticket.comment is null or undefined', () => {
     const freshFixture = TestBed.createComponent(TicketDetailPage);
     const freshComponent = freshFixture.componentInstance;
     freshComponent.ticket = { ...TICKETS_MOCK[0], comment: null as any };
     freshFixture.detectChanges();

     const commentSection = freshFixture.debugElement.query(By.css('.ticket-comment-section'));

     expect(commentSection).toBeNull();
   });

   it('should render the comment section if ticket.comment has text', () => {
     const freshFixture = TestBed.createComponent(TicketDetailPage);
     const freshComponent = freshFixture.componentInstance;
     const testComment = 'This is a test comment for testing purposes.';
     freshComponent.ticket = { ...TICKETS_MOCK[0], comment: testComment };
     freshFixture.detectChanges();

     const commentSection = freshFixture.debugElement.query(By.css('.ticket-comment-section'));

     expect(commentSection).toBeTruthy();
     expect(commentSection.nativeElement.textContent).toContain(testComment);
   });
 });

