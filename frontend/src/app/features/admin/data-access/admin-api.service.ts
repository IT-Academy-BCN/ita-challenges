import { inject, Injectable } from '@angular/core';
import { Role } from '../../../core/models/role.enum';
import { catchError, Observable, of} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IUser } from '../../../shared/models/iuser.interface';

@Injectable({
  providedIn: 'root',
})
export class AdminApiService {
  
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/account/auth/register';

  setUserRole(username: string, role: Role): Observable<IUser> {
    const user: IUser = {username: username, role: role};
    return this.http.post<IUser>(this.apiUrl, user)
    .pipe(
      catchError(() => {
        return of(user);
      })
    );
  }

}
