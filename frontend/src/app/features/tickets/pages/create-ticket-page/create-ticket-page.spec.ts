import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTicketPage } from './create-ticket-page';
import { TicketApiService } from '../../data-access/ticket-api.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('CreateTicketPage', () => {
  let component: CreateTicketPage;
  let fixture: ComponentFixture<CreateTicketPage>;
  let mockTicketService: Partial<TicketApiService>;
  let mockRouter: Partial<Router>;

  beforeEach(async () => {

    mockTicketService = {
      create: () => of({ id:'1', userId: '', title: '', description: '' })
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

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should start the form with empty fields', () => {
    const userId = component.ticketForm.get('userId');
    const title = component.ticketForm.get('title');
    const description = component.ticketForm.get('description');

    expect(userId?.value).toBe('');
    expect(title?.value).toBe('');
    expect(description?.value).toBe('');
  });

  it('should call onSubmit when form is submitted', () => {
    vi.spyOn(component, 'onSubmit');

    const form = fixture.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('submit'));

    expect(component.onSubmit).toHaveBeenCalled();
  });

  it('should call ticketApiService.create when call onSubmit with correct data', () => {
    const testData = { userId: 'usuari', title: 'Nou Repte', description: 'Descripció' };
    component.ticketForm.setValue(testData);

    vi.spyOn(mockTicketService, 'create').mockReturnValue(of({ id: '1', ...testData }));

    component.onSubmit();

    expect(mockTicketService.create).toHaveBeenCalledWith(testData);
  });  

  it('should call goTickets when call onSubmit', () => {
    vi.spyOn(component, 'goTickets');

    component.onSubmit();

    expect(component.goTickets).toHaveBeenCalled();
  });

  it('should navigate when call goTickets', () => {
    component.goTickets();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/tickets']);
  });  
});
