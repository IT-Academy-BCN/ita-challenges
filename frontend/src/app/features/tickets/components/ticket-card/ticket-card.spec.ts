import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { TicketCard } from './ticket-card';
import { TicketStatus } from '../../models/status.enum';
import { AssignableUser } from '../../models/assignable-user.interface';

describe('TicketCard', () => {
  let component: TicketCard;
  let fixture: ComponentFixture<TicketCard>;
  const mockUsers: AssignableUser[] = [{ username: 'mentor1', role: 'mentor' }];
  const mockTicket = { id: '1', userId: 'u-1', title: 'T', description: 'D', status: TicketStatus.OPEN, comment: null, assignedTo: 'mentor1', createdAt: new Date(), updatedAt: new Date() };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketCard],
      providers: [provideRouter([])]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketCard);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('ticket', mockTicket);
    fixture.componentRef.setInput('assignableUsers', mockUsers);
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should mark "Sense assignar" as selected when assignedTo is null', () => {
    fixture.componentRef.setInput('ticket', { ...mockTicket, assignedTo: null });
    fixture.detectChanges();
    expect((fixture.nativeElement.querySelector('option') as HTMLOptionElement).selected).toBe(true);
  });
});
