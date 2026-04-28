import { inject, Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { Observable, of } from 'rxjs';
import { ChallengeApiService } from '../data-access/challenge-api.service';

@Injectable({
  providedIn: 'root',
})

export class ChallengeService {

  challengeApiService = inject(ChallengeApiService)

  create(challenge: IChallengeRequest): Observable<IChallenge>  {
    return of ({
      id: "1",
      title: challenge.title,
      description:challenge.description,
    })
  }

  loadAll(): Observable<IChallenge[]> {
    return this.challengeApiService.loadAll();
  }

  update(id: string, challenge: IChallengeRequest): Observable<IChallenge> {
    return this.challengeApiService.update(id, challenge)
  }

  delete(id: string): Observable<void> { return this.challengeApiService.delete(id) }

}
