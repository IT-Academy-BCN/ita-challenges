import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { ChallengesListPage } from './challenges-list-page';
import { ChallengeService } from '../../services/challenge.service';
import { CHALLENGES_MOCK } from '../../models/challenges.mock';

describe('ChallengesListPage', () => {
  let component: ChallengesListPage;
  let fixture: ComponentFixture<ChallengesListPage>;
  let mockChallengeService: any;

  beforeEach(async () => {
    mockChallengeService = {
      loadAll: () => of(CHALLENGES_MOCK)
    };

    await TestBed.configureTestingModule({
      imports: [ChallengesListPage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengesListPage);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load challenges on initialization', () => {
    expect(component.challenges).toEqual(CHALLENGES_MOCK);
  });

  it('should render challenges in the template', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const listItems = compiled.querySelectorAll('li');
    expect(listItems.length).toBe(CHALLENGES_MOCK.length);
    expect(listItems[0].textContent).toContain(CHALLENGES_MOCK[0].title);
  });
});
