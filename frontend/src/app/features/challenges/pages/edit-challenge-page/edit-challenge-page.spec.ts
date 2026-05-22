import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditChallengePage } from './edit-challenge-page';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { ChallengeLanguage } from '../../models/challenge-language.type';

describe('EditChallengePage', () => {
  let component: EditChallengePage;
  let fixture: ComponentFixture<EditChallengePage>;
  let mockChallengeService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockChallengeService = {
      update: vi.fn().mockReturnValue(of({})),
    };
    mockRouter = {
      navigate: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [EditChallengePage, ReactiveFormsModule],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: Router, useValue: mockRouter },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EditChallengePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call challengeService.update with correct values and navigate', () => {
    const testData = {
      id: '777',
      title: 'Repte Editat',
      description: 'Nova descripció',
      language: 'JAVA',
    };
    component.editForm.setValue(testData);

    const navigateSpy = vi.spyOn(component, 'goChallenges');

    component.onSubmit();

    expect(mockChallengeService.update).toHaveBeenCalledWith('777', {
      title: 'Repte Editat',
      description: 'Nova descripció',
      language: 'JAVA',
    });

    expect(navigateSpy).toHaveBeenCalled();
  });

  it('should navigate to /challenges when goChallenges is called', () => {
    component.goChallenges();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/challenges']);
  });
});
