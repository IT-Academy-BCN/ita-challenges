import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of } from 'rxjs';
import { vi } from 'vitest';
import { ChallengeService } from './challenge.service';
import { ChallengeApiService } from '../data-access/challenge-api.service';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../models/challenges.mock';


describe('ChallengeService', () => {
  let service: ChallengeService;
  let mockChallengeApiService: Partial<ChallengeApiService>;

  beforeEach(() => {
    mockChallengeApiService = {
      delete: (id: string) => of(undefined),
      loadAll: () => of(CHALLENGES_MOCK),
      update: vi.fn(),
      create: vi.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: ChallengeApiService, useValue: mockChallengeApiService }
      ]
    });
    service = TestBed.inject(ChallengeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('update', () => {
    it('should call challengeApiService.update with correct data and return the same observable', () => {
      const testId = '123';
      const testChallenge: IChallengeRequest = {
        title: 'New Title',
        description: 'New description',
        language: 'JAVA',
        solution: `New solution`
      };

      mockChallengeApiService.update = vi.fn((id, data) =>
        of({ id, ...data } as IChallenge)
      );

      const result = service.update(testId, testChallenge);

      expect(mockChallengeApiService.update).toHaveBeenCalledWith(testId, testChallenge);

      result.subscribe(response => {
        expect(response.id).toBe(testId);
        expect(response.title).toBe(testChallenge.title);
        });
      });
    });


  describe('create', () => {
    it('should call challengeApiService.create with correct data and return the observable', () => {
      const newChallenge: IChallengeRequest = { 
        title: 'New', 
        description: 'Desc', 
        language: 'JAVA', 
        solution: 'this'
      };

      mockChallengeApiService.create = vi.fn((data) =>
        of({ id: '1', ...data } as IChallenge)
      );

      const result = service.create(newChallenge);

      expect(mockChallengeApiService.create).toHaveBeenCalledWith(newChallenge);

      result.subscribe(response => {
        expect(response.title).toBe(newChallenge.title);
        expect(response.description).toBe(newChallenge.description);
        expect(response.language).toBe(newChallenge.language);
      });
    });
  });

  describe('delete', () => {
    it('should call challengeApiService.delete with the correct id and return its observable', () => {
      const testId = '123';
      const expectedObservable = of(undefined);
      vi.spyOn(mockChallengeApiService, 'delete').mockReturnValue(expectedObservable);

      const result = service.delete(testId);

      expect(mockChallengeApiService.delete).toHaveBeenCalledWith(testId);
      expect(result).toBe(expectedObservable);
    });
  });

  it('should return challenges from loadAll', () => {
    let result: any;
    service.loadAll().subscribe((challenges) => {
      result = challenges;
    });
    expect(result).toEqual(CHALLENGES_MOCK);
  });

  describe('getById', () => {
  it('should return the correct challenge when id exists', async () => {
    const spy = vi.spyOn(mockChallengeApiService, 'loadAll');
    const challenge = await firstValueFrom(service.getById(CHALLENGES_MOCK[0].id));
    expect(challenge).toEqual(CHALLENGES_MOCK[0]);
    expect(spy).toHaveBeenCalled();
  });

  it('should return undefined when id does not exist', async () => {
    const challenge = await firstValueFrom(service.getById('inexistente'));
    expect(challenge).toBeUndefined();
  });
});
});
