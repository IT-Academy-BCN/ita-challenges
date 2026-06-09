import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { provideRouter, RouterLink } from '@angular/router';

import { ChallengesListPage } from './challenges-list-page';
import { ChallengeService } from '../../services/challenge.service';
import { CreateButtonComponent } from '../../components/buttons/create-button/create-button';
import { RoleSelectorComponent } from '../../components/role-selector/role-selector';
import { CHALLENGES_MOCK } from '../../models/challenges.mock';

describe('ChallengesListPage', () => {
  let component: ChallengesListPage;
  let fixture: ComponentFixture<ChallengesListPage>;
  let mockChallengeService: any;

  beforeEach(async () => {
    mockChallengeService = {
      loadAll: vi.fn().mockReturnValue(of(CHALLENGES_MOCK)),
      delete: vi.fn().mockReturnValue(of(void 0)),
    };

    await TestBed.configureTestingModule({
      imports: [ChallengesListPage],
      providers: [
        provideRouter([]),
        { provide: ChallengeService, useValue: mockChallengeService }
      ],
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
    const cards = fixture.nativeElement.querySelectorAll('.challenge-card');

    expect(cards.length).toBe(CHALLENGES_MOCK.length);
    expect(cards[0].textContent).toContain(CHALLENGES_MOCK[0].title);
  });

  it('should return correct difficulty and language labels', () => {
    expect(component.getDifficultyLabel('EASY')).toBe('Fàcil');
    expect(component.getDifficultyLabel('MEDIUM')).toBe('Mitjana');
    expect(component.getDifficultyLabel('HARD')).toBe('Difícil');
    expect(component.getDifficultyLabel()).toBe('');

    expect(component.getLanguageLabel('JAVA')).toBe('Java');
    expect(component.getLanguageLabel('PYTHON')).toBe('Python');
    expect(component.getLanguageLabel('JAVASCRIPT')).toBe('JavaScript');
    expect(component.getLanguageLabel('TYPESCRIPT')).toBe('TypeScript');
    expect(component.getLanguageLabel('PHP')).toBe('PHP');
    expect(component.getLanguageLabel('SQL')).toBe('SQL');
    expect(component.getLanguageLabel()).toBe('');
  });

  it('should render role selector component', () => {
    const roleSelector = fixture.debugElement.query(By.directive(RoleSelectorComponent));
    expect(roleSelector).toBeTruthy();
  });

  it('should not render create button when student', () => {
    component.isMentor.set(false);
    fixture.detectChanges();
    const button = fixture.debugElement.query(By.directive(CreateButtonComponent));
    expect(button).toBeNull();
  });

  it('should render create button when mentor', () => {
    component.isMentor.set(true);
    fixture.detectChanges();
    const button = fixture.debugElement.query(By.directive(CreateButtonComponent));
    expect(button).toBeTruthy();
  });

  it('should have routerLink buttons for each challenge', () => {
    const links = fixture.debugElement.queryAll(By.directive(RouterLink));
    expect(links.length).toBeGreaterThan(0);
  });

  it('should update isMentor when onRoleChange is called', () => {
    component.onRoleChange(true);
    expect(component.isMentor()).toBe(true);

    component.onRoleChange(false);
    expect(component.isMentor()).toBe(false);
  });

  it('should delete a challenge and remove it from the list', () => {
    const initialLength = component.challenges().length;

    component.handleDelete(CHALLENGES_MOCK[0].id);

    expect(mockChallengeService.delete).toHaveBeenCalledWith(CHALLENGES_MOCK[0].id);
    expect(component.challenges().length).toBe(initialLength - 1);
    expect(component.challenges().some(c => c.id === CHALLENGES_MOCK[0].id)).toBe(false);
  });
});
