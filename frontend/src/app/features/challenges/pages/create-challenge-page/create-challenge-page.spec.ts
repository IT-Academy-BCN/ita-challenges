import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateChallengePage } from './create-challenge-page';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';

describe('CreateChallengePage', () => {
  let component: CreateChallengePage;
  let fixture: ComponentFixture<CreateChallengePage>;
  let mockChallengeService: Partial<ChallengeService>;
  let mockRouter: Partial<Router>;

  beforeEach(async () => {

    mockChallengeService = {
      create: () => of({ id: '1', title: '', description: '', solution: '', difficulty: 'EASY' })
    };

    mockRouter = {
      navigate: vi.fn() as any
    };

    await TestBed.configureTestingModule({
      imports: [CreateChallengePage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: Router, useValue: mockRouter }
      ]
    })
    .compileComponents();

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
    const difficulty = component.challengeForm.get('difficulty');
    const solution = component.challengeForm.get('solution');

    expect(title?.value).toBe('');
    expect(description?.value).toBe('');
    expect(solution?.value).toBe('');
    expect(difficulty?.value).toBe('');
  });

  it('should call onSubmit when form is submitted', () => {
    vi.spyOn(component, 'onSubmit');

    const form = fixture.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('submit'));

    expect(component.onSubmit).toHaveBeenCalled();
  });

  it('should call challengeService.create when call onSubmit with correct data', () => {
    const testData = { title: 'Nou Repte', description: 'Descripció', solution: 'Solució', difficulty: 'EASY' as ChallengeDifficulty };
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
});
