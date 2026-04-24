import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteChallengePage } from './delete-challenge-page';

describe('DeleteChallengePage', () => {
  let component: DeleteChallengePage;
  let fixture: ComponentFixture<DeleteChallengePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteChallengePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
