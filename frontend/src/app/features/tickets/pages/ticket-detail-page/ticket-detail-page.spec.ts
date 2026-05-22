import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketDetailPage } from './ticket-detail-page';

describe('TicketDetailPage', () => {
  let component: TicketDetailPage;
  let fixture: ComponentFixture<TicketDetailPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketDetailPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketDetailPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
