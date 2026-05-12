import { inject, Injectable } from '@angular/core';
import { Role } from '../../../core/models/role.enum';
import { Observable} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IUser } from '../../../shared/models/iuser.interface';

@Injectable({
  providedIn: 'root',
})
export class AdminApiService {
  
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/account/users';

  setUserRole(username: string, role: Role): Observable<void> {
    const user: IUser = {username: username, role: role};
    return this.http.post<void>(this.apiUrl, user);
  }

}
