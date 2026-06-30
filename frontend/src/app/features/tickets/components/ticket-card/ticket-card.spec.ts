import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { provideRouter } from '@angular/router';
import { TicketCard } from './ticket-card';
import { TicketService } from '../../ticket.service';
import { ITicket } from '../../models/iticket.interface';
import { TicketStatus } from '../../models/status.enum';

const mockUsers = [{ username: 'mentor1', role: 'mentor' }];
const mockTicket: ITicket = {
  id: '1', userId: 'u1', title: 'Test', description: 'desc',
  status: TicketStatus.OPEN, comment: null, mentorAssignedId: 'mentor1',
  createdAt: new Date(), updatedAt: new Date(),
};

describe('TicketCard', () => {
  let component: TicketCard;
  let fixture: ComponentFixture<TicketCard>;
  let ticketService: jasmine.SpyObj<TicketService>;

  beforeEach(async () => {
    ticketService = jasmine.createSpyObj('TicketService', ['updateAssignedMentor']);

    await TestBed.configureTestingModule({
      imports: [TicketCard],
      providers: [
        { provide: TicketService, useValue: ticketService },
        provideRouter([]),
      ],
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

  it('initializes selectedMentorId from ticket', () => {
    expect(component.selectedMentorId()).toBe('mentor1');
  });

  it('calls updateAssignedMentor on select change', () => {
    ticketService.updateAssignedMentor.and.returnValue(of({ ...mockTicket, mentorAssignedId: 'mentor2' }));
    component.onSelectChange({ target: { value: 'mentor2' } } as unknown as Event);
    expect(ticketService.updateAssignedMentor).toHaveBeenCalledWith('1', 'mentor2');
  });

  it('reverts selectedMentorId on API error', () => {
    ticketService.updateAssignedMentor.and.returnValue(throwError(() => new Error()));
    component.onSelectChange({ target: { value: 'mentor2' } } as unknown as Event);
    expect(component.selectedMentorId()).toBe('mentor1');
  });
});