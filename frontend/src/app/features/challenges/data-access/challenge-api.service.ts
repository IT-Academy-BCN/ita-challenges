import { inject, Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { catchError, Observable, of } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CHALLENGES_MOCK } from '../models/challenges.mock';
import { IChallengeSubmission } from '../models/ichallenge-submission.interface';

@Injectable({
  providedIn: 'root',
})
export class ChallengeApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/challenge';

  create(challenge: IChallengeRequest): Observable<IChallenge> {
    return this.http.post<IChallenge>(this.apiUrl, challenge).pipe(
      catchError(() => {
        return of({
          id: '1',
          title: challenge.title,
          description: challenge.description,
        });
      }),
    );
  }

  loadAll(): Observable<IChallenge[]> {
    return this.http.get<IChallenge[]>(this.apiUrl).pipe(
      catchError((error: HttpErrorResponse) => {
        return of(CHALLENGES_MOCK);
      }),
    );
  }

  update(id: string, challenge: IChallengeRequest): Observable<IChallenge> {
    return this.http.put<IChallenge>(`${this.apiUrl}/${id}`, challenge).pipe(
      catchError(() =>
        of({
          id: id,
          title: challenge.title,
          description: challenge.description,
        }),
      ),
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError((error: HttpErrorResponse) => {
        return of(undefined);
      }),
    );
  }

  postSolution(payload: IChallengeSubmission): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/submissions/finalize`, payload);
  }
}
