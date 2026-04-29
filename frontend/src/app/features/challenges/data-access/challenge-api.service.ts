import { inject, Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ChallengeApiService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/challenge';

  create(challenge: IChallengeRequest): Observable<IChallenge> {
    return this.http.post<IChallenge>(this.apiUrl, challenge);
  }

  loadAll(): Observable<IChallenge[]> {
    return of([])
  }

  update(id: string, challenge: IChallengeRequest): Observable<IChallenge> {
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
