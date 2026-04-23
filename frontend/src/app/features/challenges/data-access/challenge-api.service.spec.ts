import { TestBed } from '@angular/core/testing';

import { ChallengeApiService } from './challenge-api.service';

describe('ChallengeApiService', () => {
  let service: ChallengeApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChallengeApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
