import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketListPage } from './ticket-list-page';

describe('TicketListPage', () => {
  let component: TicketListPage;
  let fixture: ComponentFixture<TicketListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketListPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketListPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
