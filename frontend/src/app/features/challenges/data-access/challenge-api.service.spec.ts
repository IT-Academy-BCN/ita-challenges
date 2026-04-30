import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ChallengeApiService } from './challenge-api.service';
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

  describe('delete', () => {
    it('should call DELETE with the correct URL', () => {
      const mockId = '123';
      
      service.delete(mockId).subscribe();

      const req = httpTestingController.expectOne(`http://localhost:8080/api/challenge/${mockId}`);
      expect(req.request.method).toBe('DELETE');
      req.flush(null);
    });

    it('should return undefined if HTTP error happens', () => {
      const mockId = '456';
      let responseResult: any = 'initial_value'; 
      
      service.delete(mockId).subscribe(result => responseResult = result);

      const req = httpTestingController.expectOne(`http://localhost:8080/api/challenge/${mockId}`);
      req.flush('Not Found', { status: 404, statusText: 'Not Found' }); 
      
      expect(responseResult).toBeUndefined();
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
