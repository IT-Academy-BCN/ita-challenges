import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { provideRouter, RouterLink } from '@angular/router';

import { ChallengesListPage } from './challenges-list-page';
import { ChallengeService } from '../../services/challenge.service';
import { CreateButtonComponent } from '../../components/buttons/create-button/create-button';
import { CHALLENGES_MOCK } from '../../models/challenges.mock';

describe('ChallengesListPage', () => {
  let component: ChallengesListPage;
  let fixture: ComponentFixture<ChallengesListPage>;
  let mockChallengeService: any;

  beforeEach(async () => {
    mockChallengeService = { loadAll: vi.fn().mockReturnValue(of(CHALLENGES_MOCK))};

    await TestBed.configureTestingModule({
      imports: [ChallengesListPage],
      providers: [
        provideRouter([]),
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
    expect(mockChallengeService.loadAll).toHaveBeenCalled();
    expect(component.challenges()).toEqual(CHALLENGES_MOCK);
  });

  it('should render challenges in the template', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const listItems = compiled.querySelectorAll('li');

    expect(listItems.length).toBe(CHALLENGES_MOCK.length);
    expect(listItems[0].textContent).toContain(CHALLENGES_MOCK[0].title);
  });

  it('should render create button component', () => {
    const button = fixture.debugElement.query(By.directive(CreateButtonComponent));
    expect(button).toBeTruthy();
  });

  it('should have routerLink buttons for each challenge', () => {
    const links = fixture.debugElement.queryAll(By.directive(RouterLink));
    expect(links.length).toBeGreaterThan(0);
  });
});
