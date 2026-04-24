import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateChallengePage } from './create-challenge-page';

describe('CreateChallengePage', () => {
  let component: CreateChallengePage;
  let fixture: ComponentFixture<CreateChallengePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateChallengePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
