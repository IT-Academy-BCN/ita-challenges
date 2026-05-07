import { Injectable } from '@angular/core';
import { Role } from '../../../core/models/role.enum';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminApiService {
  
  setUserRole(username: string, role: Role): Observable<void>{
    return of(undefined)
  }

}
