import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TicketDetailPage } from './ticket-detail-page';
import { TICKETS_MOCK } from '../../models/tickets.mock';

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
});
