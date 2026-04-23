import { Injectable, signal } from '@angular/core';
import { AuthUser } from '../models/auth-user.model';
import { EMPTY, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user = signal<AuthUser | null>(null);

  loginWithGithub(): Observable<AuthUser> {
    return EMPTY
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser():AuthUser | null {
    return this.user();
  }

}
