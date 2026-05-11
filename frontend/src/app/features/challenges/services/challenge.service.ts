import { inject, Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { map, Observable } from 'rxjs';
import { ChallengeApiService } from '../data-access/challenge-api.service';

@Injectable({
  providedIn: 'root',
})

export class ChallengeService {

  private readonly challengeApiService = inject(ChallengeApiService)

  create(challenge: IChallengeRequest): Observable<IChallenge>  {
    return this.challengeApiService.create(challenge);
  }

  loadAll(): Observable<IChallenge[]> {
    return this.challengeApiService.loadAll();
  }

  update(id: string, challenge: IChallengeRequest): Observable<IChallenge> {
    return this.challengeApiService.update(id, challenge)
  }

  delete(id: string): Observable<void> { return this.challengeApiService.delete(id) }

  getById(id: string): Observable<IChallenge | undefined> {
    return this.challengeApiService.loadAll().pipe(
      map((challenges) => challenges.find(((challenge) => challenge.id === id)))
    )
  }

}
