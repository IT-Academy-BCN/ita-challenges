import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketButton } from './ticket-button';

describe('Button', () => {
  let component: TicketButton;
  let fixture: ComponentFixture<TicketButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});