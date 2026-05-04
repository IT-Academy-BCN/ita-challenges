import { inject, Injectable, signal } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthUser } from '../models/auth-user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);

  user = signal<AuthUser | null>(null);

  private readonly mockUser: AuthUser = {
    username: 'mockUser',
    avatarUrl: 'https://github.com/MockUser.png',
    token: 'token-808'
  };

  loginWithGithub(): void {
    this.setUser(this.mockUser);

    globalThis.location.href = '/profile';
  }

  fetchUser(): Observable<AuthUser> {
    return of(this.mockUser);
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }
}