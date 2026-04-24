import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengesListPage } from './challenges-list-page';

describe('ChallengesListPage', () => {
  let component: ChallengesListPage;
  let fixture: ComponentFixture<ChallengesListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengesListPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengesListPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
