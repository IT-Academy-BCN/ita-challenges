import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTicketPage } from './create-ticket-page';

describe('CreateTicketPage', () => {
  let component: CreateTicketPage;
  let fixture: ComponentFixture<CreateTicketPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTicketPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateTicketPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
