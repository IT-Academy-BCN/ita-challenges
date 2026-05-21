import { TestBed } from '@angular/core/testing';
import { ChallengeApiService } from './challenge-api.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../models/challenges.mock';
import { IChallengeSubmission } from '../models/ichallenge-submission.interface';

describe('ChallengeApiService', () => {
  let service: ChallengeApiService;
  let httpTestingController: HttpTestingController;
  const apiUrl = 'http://localhost:8080/api/challenge';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(ChallengeApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('update', () => {
    const testId = 'abc-123';
    const updateData: IChallengeRequest = {
      title: 'Updated Challenge',
      description: 'New Description',
      difficulty: 'EASY',
    };

    it('should call PUT with correct URL and body', () => {
      const mockResponse: IChallenge = { id: testId, ...updateData };

      service.update(testId, updateData).subscribe(result => {
        expect(result).toEqual(mockResponse);
      });

      const req = httpTestingController.expectOne(`${apiUrl}/${testId}`);
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(updateData);

      req.flush(mockResponse);
    });

    it('should return reconstructed challenge when API fails (Happy Path Fallback)', () => {
      service.update(testId, updateData).subscribe(result => {
        expect(result.id).toBe(testId);
        expect(result.title).toBe(updateData.title);
      });

      const req = httpTestingController.expectOne(`${apiUrl}/${testId}`);

      req.flush('Error', { status: 500, statusText: 'Server Error' });
    });
  });


  describe('create', () => {
    it('should call POST with the correct URL and body and return the created challenge', () => {
      const newChallenge: IChallengeRequest = { title: 'Test', description: 'Desc' };
      const mockResponse: IChallenge = { id: '1', ...newChallenge };

      service.create(newChallenge).subscribe(result => {
        expect(result).toEqual(mockResponse);
      });

      const req = httpTestingController.expectOne(apiUrl);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(newChallenge);
      req.flush(mockResponse, { status: 201, statusText: 'Created' });
    });

    it('should return default challenge when API fails (Happy Path Fallback)', () => {
      const newChallenge: IChallengeRequest = { title: 'Test', description: 'Desc', difficulty: 'EASY' };

      service.create(newChallenge).subscribe(response => {
        expect(response.id).toBe('1');
        expect(response.title).toBe(newChallenge.title);
      });

      const req = httpTestingController.expectOne(apiUrl);

      req.flush('Error', { status: 500, statusText: 'Server Error' });
    });
  });


  describe('loadAll()', () => {
    it('should load all challenges via GET', () => {
      service.loadAll().subscribe((challenges) => {
        expect(challenges).toEqual(CHALLENGES_MOCK);
      });

      const req = httpTestingController.expectOne(apiUrl);
      expect(req.request.method).toBe('GET');

      req.flush(CHALLENGES_MOCK);
    });

    it('should return mocked challenges on HTTP error', () => {
      service.loadAll().subscribe((challenges) => {
        expect(challenges).toEqual(CHALLENGES_MOCK);
      });

      const req = httpTestingController.expectOne(apiUrl);

      req.flush('Error interno', { status: 500, statusText: 'Internal Server Error' });
    });
  });

  describe('delete', () => {
    it('should call DELETE with the correct URL', () => {
      const mockId = '123';

      service.delete(mockId).subscribe();

      const req = httpTestingController.expectOne(`${apiUrl}/${mockId}`);
      expect(req.request.method).toBe('DELETE');
      req.flush(null);
    });

    it('should return undefined if HTTP error happens', () => {
      const mockId = '456';
      let responseResult: any = 'initial_value';

      service.delete(mockId).subscribe(result => responseResult = result);

      const req = httpTestingController.expectOne(`${apiUrl}/${mockId}`);
      req.flush('Not Found', { status: 404, statusText: 'Not Found' });

      expect(responseResult).toBeUndefined();
    });
  });

  describe('postSolution', () => {
  it('should call POST with correct URL and payload', () => {
    const payload: IChallengeSubmission = {
      challengeId: 'abc-123',
      userId: 'abc-345',
      code: 'code'
    };

    service.postSolution(payload).subscribe();

    const req = httpTestingController.expectOne(`${apiUrl}/submissions/finalize`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);
    req.flush(null);
  });
});
});
