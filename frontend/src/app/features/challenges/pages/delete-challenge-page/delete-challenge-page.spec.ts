import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteChallengePage } from './delete-challenge-page';
import { ChallengeService } from '../../services/challenge.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('DeleteChallengePage', () => {
  let component: DeleteChallengePage;
  let fixture: ComponentFixture<DeleteChallengePage>;
  let mockChallengeService: Partial<ChallengeService>;
  let mockRouter: Partial<Router>;

  beforeEach(async () => {
    mockChallengeService = {
      delete: (id: string) => of(undefined)
    };
    mockRouter = {
      navigate: vi.fn() as any
    };
    await TestBed.configureTestingModule({
      imports: [DeleteChallengePage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService},
        { provide: Router, useValue: mockRouter }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should start the form with empty field', () => {
    const id = component.deleteChallengeForm.get('challengeId')
    
    expect(id?.value).toBe('')
  })

  it('should call onSubmit when form is submitted', () => {
    vi.spyOn(component, 'onSubmit')

    const form = fixture.nativeElement.querySelector('form')
    form.dispatchEvent(new Event('submit'));
    
    expect(component.onSubmit).toHaveBeenCalled();
  })

  it('should call challengeService.delete when call OnSubmit with correct challengeId', () => {
    const testId = '123'
    component.deleteChallengeForm.setValue({challengeId: testId})

    vi.spyOn(mockChallengeService, 'delete').mockReturnValue(of(undefined))

    component.onSubmit()
    expect(mockChallengeService.delete).toHaveBeenCalledWith(testId);
  })

  it('should call goChallenges when call onSubmit', () => {
    vi.spyOn(component, 'goChallenges')

    component.onSubmit()
    expect(component.goChallenges).toHaveBeenCalled();
  })

  it('should navigate when call goChallenges', () => {
    component.goChallenges()
    expect(mockRouter.navigate).toHaveBeenCalled();
  })
});
