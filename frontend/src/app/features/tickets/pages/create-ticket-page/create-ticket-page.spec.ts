import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateTicketPage } from './create-ticket-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TicketStatus } from '../../models/status.enum';

describe('CreateTicketPage', () => {
  let component: CreateTicketPage;
  let fixture: ComponentFixture<CreateTicketPage>;
  let mockTicketService: Partial<TicketApiService>;
  let mockRouter: Partial<Router>;

  beforeEach(async () => {
    mockTicketService = {
      create: () => of({ id:'1', userId: 'one', title: '', description: '', status: TicketStatus.OPEN, comment: null, createdAt: new Date(), updatedAt: new Date()  })
    };

    mockRouter = {
      navigate: vi.fn() as any
    };

    await TestBed.configureTestingModule({
      imports: [CreateTicketPage],
      providers: [
        { provide: TicketApiService, useValue: mockTicketService },
        { provide: Router, useValue: mockRouter }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateTicketPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it('should call ticketApiService.create when call onSubmit with correct data', () => {
    const testData = { title: 'Nou Repte', description: 'Descripció' };
    component.ticketForm.setValue(testData);
    vi.spyOn(mockTicketService, 'create').mockReturnValue(of({ id: '1', userId: 'usuari', ...testData, status: TicketStatus.OPEN, comment: null, createdAt: new Date(), updatedAt: new Date() }));
    component.onSubmit();
    expect(mockTicketService.create).toHaveBeenCalledWith(testData);
  });

  it('should show the success message on 201 and navigate to /tickets after the delay', () => {
    vi.useFakeTimers();
    const testData = { title: 'Nou Repte', description: 'Descripció' };
    component.ticketForm.setValue(testData);
    component.onSubmit();

    expect(component.ticketMessage()).toBe('Ticket creat correctament');
    expect(mockRouter.navigate).not.toHaveBeenCalled();

    vi.advanceTimersByTime(2000);

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/tickets']);
  });

  it('should show the "dades incorrectes" message on a 400 error and not navigate', () => {
    vi.spyOn(mockTicketService, 'create').mockReturnValue(throwError(() => ({ status: 400 })));
    const testData = { title: '', description: '' };
    component.ticketForm.setValue(testData);
    component.onSubmit();

    expect(component.ticketMessage()).toBe('Dades del ticket incorrectes');
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });
});
