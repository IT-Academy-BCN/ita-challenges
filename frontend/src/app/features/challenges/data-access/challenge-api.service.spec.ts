import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { ChallengeApiService } from './challenge-api.service';
import { IChallenge } from '../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../models/challenges.mock';

describe('ChallengeApiService', () => {
  let service: ChallengeApiService;
  let httpTestingController: HttpTestingController;

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

  describe('create', () => {
    it('should call POST with the correct URL and body and return the created challenge', () => {
      const newChallenge: IChallengeRequest = { title: 'Test', description: 'Desc' };
      const mockResponse: IChallenge = { id: '1', ...newChallenge };

      service.create(newChallenge).subscribe(result => {
        expect(result).toEqual(mockResponse);
      });

      const req = httpTestingController.expectOne('http://localhost:8080/api/challenge');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(newChallenge);
      req.flush(mockResponse, { status: 201, statusText: 'Created' });
    });

    it('should return default challenge when API fails (Happy Path Fallback)', () => {
      const newChallenge: IChallengeRequest = { title: 'Test', description: 'Desc' };

      service.create(newChallenge).subscribe(response => {
        expect(response.id).toBe('1');
        expect(response.title).toBe(newChallenge.title);
      });

      const req = httpTestingController.expectOne('http://localhost:8080/api/challenge');

      req.flush('Error', { status: 500, statusText: 'Server Error' });
    });
  });


  describe('loadAll()', () => {
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
});
