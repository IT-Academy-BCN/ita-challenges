import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateTicketPage } from './create-ticket-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { of } from 'rxjs';
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

  it('should call ticketApiService.create when call onSubmit with correct data', () => {
    const testData = { title: 'Nou Repte', description: 'Descripció' };
    component.ticketForm.setValue(testData);
    vi.spyOn(mockTicketService, 'create').mockReturnValue(of({ id: '1', userId: 'usuari', ...testData, status: TicketStatus.OPEN, comment: null, createdAt: new Date(), updatedAt: new Date() }));
    component.onSubmit();
    expect(mockTicketService.create).toHaveBeenCalledWith(testData);
  });
});
