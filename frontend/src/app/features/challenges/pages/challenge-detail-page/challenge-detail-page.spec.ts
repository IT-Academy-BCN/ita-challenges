import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ChallengeDetailPage } from './challenge-detail-page';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { ChallengeApiService } from '../../data-access/challenge-api.service';
import { AuthService } from '../../../auth/data-access/auth-service';

describe('ChallengeDetailPage', () => {
  let component: ChallengeDetailPage;
  let fixture: ComponentFixture<ChallengeDetailPage>;
  let mockChallengeService: any;
  let mockActivatedRoute: any;
  let mockChallengeApiService: any;
  let mockAuthService: any;

  const mockChallenge = {
    id: 'test-123',
    title: 'Test Challenge',
    description: 'Test Description',
    language: 'JAVASCRIPT',
    difficulty: 'EASY',
  };

  beforeEach(async () => {
    mockChallengeService = { getById: vi.fn().mockReturnValue(of(mockChallenge)) };
    mockActivatedRoute = { snapshot: { paramMap: { get: vi.fn().mockReturnValue('test-123') } } };
    mockChallengeApiService = {
      saveSolution: vi.fn().mockReturnValue(of({})),
      publishSolution: vi.fn().mockReturnValue(of({}))
    };
    mockAuthService = {user: vi.fn().mockReturnValue({ id: 'user-456' })};

    await TestBed.configureTestingModule({
      imports: [ChallengeDetailPage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: ChallengeApiService, useValue: mockChallengeApiService },
        { provide: AuthService, useValue: mockAuthService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ChallengeDetailPage);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load challenge on init', async () => {
    fixture.detectChanges();
    await fixture.whenStable();
    expect(component.challenge()).toEqual(mockChallenge);
  });

  it('should keep challenge undefined when not found', async () => {
    mockChallengeService.getById.mockReturnValue(of(undefined));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(component.challenge()).toBeUndefined();
  });

  it('should display language and difficulty in template', async () => {
    fixture.detectChanges();
    await fixture.whenStable();

    const element = fixture.nativeElement as HTMLElement;

    expect(element.textContent).toContain('Test Challenge');
    expect(element.textContent).toContain('Test Description');
    expect(element.textContent).toContain('Llenguatge');
    expect(element.textContent).toContain('Dificultat');
  });

  it('should call saveSolution with form data and challengeId', () => {
    fixture.detectChanges();
    component.codeSolutionForm.patchValue({ code: 'my-code' });
    component.saveSolution();

    expect(mockChallengeApiService.saveSolution).toHaveBeenCalledWith({
      challengeId: 'test-123',
      userId: 'user-456',
      code: 'my-code'
    });
  });

  it('should call publishSolution with form data and challengeId', () => {
    fixture.detectChanges();
    component.codeSolutionForm.patchValue({ code: 'my-code' });
    component.publishSolution();

    expect(mockChallengeApiService.publishSolution).toHaveBeenCalledWith({
      challengeId: 'test-123',
      userId: 'user-456',
      code: 'my-code'
    });
  });

  it('should set isMentor signal with the received value', () => {
    component.onRoleChange(true);
    expect(component.isMentor()).toBe(true);
  });

  it('should set programmingMode signal to true', () => {
    component.setProgrammingMode();
    expect(component.programmingMode()).toBe(true);
  });
});
