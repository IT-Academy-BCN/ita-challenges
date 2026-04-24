import { Injectable, signal } from '@angular/core';
import { AuthUser } from '../models/auth-user.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user = signal<AuthUser | null>(null);

  loginWithGithub(): Observable<AuthUser> {
    const user: AuthUser = {
      username: 'JordiMiravet',
      avatarUrl: 'https://github.com/JordiMiravet.png',
    };

    return of(user);
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }

}
