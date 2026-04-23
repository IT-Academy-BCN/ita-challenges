import { Injectable } from '@angular/core';
import { IChallenge } from '../models/ichallenge.interface';
import { IChallengeResult } from '../models/ichallenge-result.interface';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChallengeApiService {
  create(challenge: IChallenge): Observable<IChallengeResult> { 
    return of({
      id: "1",
      title: challenge.title,
      description:challenge.description,
    })
  }

  loadAll(): Observable<IChallengeResult[]> {
    return of([
      {
        id: "1",
        title:"primero",
        description: "primero d"
      },
      {
        id: "2",
        title:"segundo",
        description: "segundo d"
      },
      {
        id: "3",
        title:"tercero",
        description: "tercero d"
      }
    ])
  }

  update(id: string, challenge: IChallenge): Observable<IChallengeResult> { 
    return of({
      id: id,
      title: challenge.title,
      description:challenge.description,
    })
  }

  delete(id: string): Observable<void> { 
    return of(undefined) 
  }

}
