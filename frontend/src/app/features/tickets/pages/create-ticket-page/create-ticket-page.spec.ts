import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateTicketPage } from './create-ticket-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { of } from 'rxjs';

describe('CreateTicketPage', () => {
  let component: CreateTicketPage;
  let fixture: ComponentFixture<CreateTicketPage>;
  let mockTicketService: Partial<TicketApiService>;

  beforeEach(async () => {
    mockTicketService = {
      create: () => of({ id:'1', userId: '', title: '', description: '' })
    };

    await TestBed.configureTestingModule({
      imports: [CreateTicketPage],
      providers: [
        { provide: TicketApiService, useValue: mockTicketService },
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateTicketPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should call ticketApiService.create when call onSubmit with correct data', () => {
    const testData = { userId: 'usuari', title: 'Nou Repte', description: 'Descripció' };
    component.ticketForm.setValue(testData);
    vi.spyOn(mockTicketService, 'create').mockReturnValue(of({ id: '1', ...testData }));
    component.onSubmit();
    expect(mockTicketService.create).toHaveBeenCalledWith(testData);
  });  
});
