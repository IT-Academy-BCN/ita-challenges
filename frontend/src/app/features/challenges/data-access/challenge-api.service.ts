import { inject, Injectable } from '@angular/core';
import { IChallengeRequest } from '../models/ichallenge-request.interface';
import { IChallenge } from '../models/ichallenge.interface';
import { catchError, Observable, of } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ChallengeApiService {

  readonly http = inject(HttpClient);
  
  create(challenge: IChallengeRequest): Observable<IChallenge> { 
    return of({
      id: "1",
      title: challenge.title,
      description:challenge.description,
    })
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
    return this.http.delete<void>(`http://localhost:8080/api/challenge/${id}`)
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return of(undefined);
      })
    )
  }
}
