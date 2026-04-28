import { Injectable, signal } from '@angular/core';
import { AuthUser } from '../models/auth-user.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user = signal<AuthUser | null>(null);

  loginWithGithub(): Observable<AuthUser> {
    const MOCK_USER: AuthUser = {
      username: 'MockUser',
      avatarUrl: 'https://github.com/MockUser.png',
    };

    return of(MOCK_USER);
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }

}
