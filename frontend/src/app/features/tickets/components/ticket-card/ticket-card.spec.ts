import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { TicketCard } from './ticket-card';
import { TicketStatus } from '../../models/status.enum';

describe('TicketCard', () => {
  let component: TicketCard;
  let fixture: ComponentFixture<TicketCard>;
  const mockUsers = [{ username: 'mentor1', role: 'mentor' }];
  const mockTicket = { id: '1', userId: 'u-1', title: 'T', description: 'D', status: TicketStatus.OPEN, comment: null, createdAt: new Date(), updatedAt: new Date() };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketCard],
      providers: [provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(TicketCard);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('ticket', mockTicket);
    fixture.componentRef.setInput('assignableUsers', mockUsers);
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should render a select with one option per user plus "Sense assignar"', () => {
    const options = fixture.nativeElement.querySelectorAll('option');
    expect(options.length).toBe(mockUsers.length + 1);
  });

  it('should select "Sense assignar" when no mentor is assigned', () => {
    fixture.componentRef.setInput('ticket', { ...mockTicket, mentorAssignedId: null });
    fixture.detectChanges();
    expect((fixture.nativeElement.querySelector('option') as HTMLOptionElement).selected).toBe(true);
  });
});
