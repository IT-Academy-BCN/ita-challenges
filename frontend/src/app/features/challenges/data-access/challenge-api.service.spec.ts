import { TestBed } from '@angular/core/testing';
import { ChallengeApiService } from './challenge-api.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../models/challenges.mock';

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
      description: 'New Description'
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
  
  it('should load all challenges via GET', () => {
    service.loadAll().subscribe((challenges) => {
      expect(challenges).toEqual(CHALLENGES_MOCK);
    });

    const req = httpTestingController.expectOne('http://localhost:8080/api/challenge');
    expect(req.request.method).toBe('GET');

    req.flush(CHALLENGES_MOCK);
  });

  it('should return mocked challenges on HTTP error', () => {
    service.loadAll().subscribe((challenges) => {
      expect(challenges).toEqual(CHALLENGES_MOCK);
    });

    const req = httpTestingController.expectOne('http://localhost:8080/api/challenge');

    req.flush('Error interno', { status: 500, statusText: 'Internal Server Error' });
  });
});
