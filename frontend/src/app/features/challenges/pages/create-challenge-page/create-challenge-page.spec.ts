import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';

import { CreateChallengePage } from './create-challenge-page';
import { ChallengeService } from '../../services/challenge.service';
import { ChallengeLanguage } from '../../models/challenge-language.type';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';

describe('CreateChallengePage', () => {
  let component: CreateChallengePage;
  let fixture: ComponentFixture<CreateChallengePage>;
  let mockChallengeService: Partial<ChallengeService>;
  let mockRouter: Partial<Router>;

  beforeEach(async () => {
    mockChallengeService = {
      create: () =>
        of({
          id: '1',
          title: '',
          description: '',
          language: 'JAVA',
          difficulty: 'EASY',
          solution: '',
        }),
    };

    mockRouter = {
      navigate: vi.fn() as any,
    };

    await TestBed.configureTestingModule({
      imports: [CreateChallengePage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: Router, useValue: mockRouter },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should start the form with empty fields', () => {
    const title = component.challengeForm.get('title');
    const description = component.challengeForm.get('description');
    const language = component.challengeForm.get('language');
    const difficulty = component.challengeForm.get('difficulty');
    const solution = component.challengeForm.get('solution');

    expect(title?.value).toBe('');
    expect(description?.value).toBe('');
    expect(language?.value).toBe('');
    expect(difficulty?.value).toBe('');
    expect(solution?.value).toBe('');
  });

  it('should call onSubmit when form is submitted', () => {
    vi.spyOn(component, 'onSubmit');

    const form = fixture.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('submit'));

    expect(component.onSubmit).toHaveBeenCalled();
  });

  it('should call challengeService.create when call onSubmit with correct data', () => {
    const testData = {
      title: 'Nou Repte',
      description: 'Descripció',
      language: 'JAVA' as ChallengeLanguage,
      difficulty: 'EASY' as ChallengeDifficulty,
      solution: 'Solució',
    };
    component.challengeForm.setValue(testData);

    vi.spyOn(mockChallengeService, 'create').mockReturnValue(of({ id: '1', ...testData }));

    component.onSubmit();

    expect(mockChallengeService.create).toHaveBeenCalledWith(testData);
  });

  it('should call goChallenges when call onSubmit', () => {
    vi.spyOn(component, 'goChallenges');

    component.onSubmit();

    expect(component.goChallenges).toHaveBeenCalled();
  });

  it('should navigate when call goChallenges', () => {
    component.goChallenges();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/challenges']);
  });

  it('should update selectedLanguage and form control on language select', () => {
    component.onLanguageSelect('JAVA');
    expect(component.selectedLanguage()).toBe('JAVA');
    expect(component.challengeForm.get('language')?.value).toBe('JAVA');
  });

  it('should have 3 difficulty options', () => {
    expect(component.difficulties.length).toBe(3);
    expect(component.difficulties).toEqual(['EASY', 'MEDIUM', 'HARD']);
  });

  it('should update difficulty form control when difficulty is selected', () => {
    component.challengeForm.get('difficulty')?.setValue('MEDIUM');
    expect(component.challengeForm.get('difficulty')?.value).toBe('MEDIUM');
  });
});
