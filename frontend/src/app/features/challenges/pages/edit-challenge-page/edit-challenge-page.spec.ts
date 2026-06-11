import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditChallengePage } from './edit-challenge-page';
import { ChallengeService } from '../../services/challenge.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';

describe('EditChallengePage', () => {
  let component: EditChallengePage;
  let fixture: ComponentFixture<EditChallengePage>;
  let mockChallengeService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockChallengeService = {
      update: vi.fn().mockReturnValue(of({})),
      getById: vi.fn().mockReturnValue(of(undefined))
    };
    mockRouter = {
      navigate: vi.fn()
    };
    mockActivatedRoute = {
      snapshot: {
        paramMap: {
          get: vi.fn().mockReturnValue(null)
        }
      }
    };

    await TestBed.configureTestingModule({
      imports: [EditChallengePage, ReactiveFormsModule],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EditChallengePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call challengeService.update with correct values and navigate', () => {
    const testData = { id: '777', title: 'Repte Editat', description: 'Nova descripció', language: 'JAVA', difficulty: 'EASY', solution: 'Placeholder solució' };

    component.editForm.setValue(testData);

    const navigateSpy = vi.spyOn(component, 'goChallenges');

    component.onSubmit();

    expect(mockChallengeService.update).toHaveBeenCalledWith('777', {
      title: 'Repte Editat',
      description: 'Nova descripció',
      solution: 'Placeholder solució',
      difficulty: 'EASY',
      language: 'JAVA'
    });

    expect(navigateSpy).toHaveBeenCalled();
  });

  it('should navigate to /challenges when goChallenges is called', () => {
    component.goChallenges();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/challenges']);
  });

  it('should patch the form with challenge data when id is in route', async () => {
    const mockChallenge = {
      id: '123', title: 'Test Challenge', description: 'Test description', solution: 'Test solution',  difficulty: 'MEDIUM', language: 'TYPESCRIPT'
    };

    mockActivatedRoute.snapshot.paramMap.get.mockReturnValue('123');
    mockChallengeService.getById.mockReturnValue(of(mockChallenge));

    component.ngOnInit();

    expect(mockChallengeService.getById).toHaveBeenCalledWith('123');
    expect(component.editForm.getRawValue()).toEqual(mockChallenge);
  });

  it('should not call getById when there is no id in route', () => {
    mockActivatedRoute.snapshot.paramMap.get.mockReturnValue(null);

    component.ngOnInit();

    expect(mockChallengeService.getById).not.toHaveBeenCalled();
  });

  it('should not patch the form when getById returns undefined', () => {
    mockActivatedRoute.snapshot.paramMap.get.mockReturnValue('123');
    mockChallengeService.getById.mockReturnValue(of(undefined));

    component.ngOnInit();

    expect(component.editForm.getRawValue()).toEqual({
      id: '', title: '', description: '', solution: '', difficulty: 'EASY', language: ''
    });
  });
});
