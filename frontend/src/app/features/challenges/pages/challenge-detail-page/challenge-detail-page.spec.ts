import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ChallengeDetailPage } from './challenge-detail-page';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';

describe('ChallengeDetailPage', () => {
  let component: ChallengeDetailPage;
  let fixture: ComponentFixture<ChallengeDetailPage>;
  let mockChallengeService: any;
  let mockActivatedRoute: any;

  const mockChallenge = {
    id: 'test-123',
    title: 'Test Challenge',
    description: 'Test Description'
  };

  beforeEach(async () => {
    mockChallengeService = { getById: vi.fn().mockReturnValue(of(mockChallenge)) };
    mockActivatedRoute = { snapshot: { paramMap: { get: vi.fn().mockReturnValue('test-123') } } };

    await TestBed.configureTestingModule({
      imports: [ChallengeDetailPage],
      providers: [
        { provide: ChallengeService, useValue: mockChallengeService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ChallengeDetailPage);
    component = fixture.componentInstance;
  });

  it('should load challenge on init', async () => {
    fixture.detectChanges();
    await fixture.whenStable();

    expect(component.challenge()).toEqual(mockChallenge);
  });
});
