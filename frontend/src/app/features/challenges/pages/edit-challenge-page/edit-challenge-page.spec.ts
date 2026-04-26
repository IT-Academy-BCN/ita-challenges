import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditChallengePage } from './edit-challenge-page';

describe('EditChallengePage', () => {
  let component: EditChallengePage;
  let fixture: ComponentFixture<EditChallengePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditChallengePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
