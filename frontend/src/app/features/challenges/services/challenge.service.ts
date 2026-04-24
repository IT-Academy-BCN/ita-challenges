import { Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class ChallengeService {
  
  create(challenge: IChallengeRequest): Observable<IChallenge>  { 
    return of ({
      id: "1",
      title: challenge.title,
      description:challenge.description,
    })
  }

  loadAll(): Observable<IChallenge[]> {
    return of ([])
  }

  update(id: string, challenge: IChallengeRequest): Observable<IChallenge> { 
    return of ({
      id: id,
      title: challenge.title,
      description:challenge.description,
    })
  }

  delete(id: string): Observable<void> { return of(undefined) }

}
