import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthUser } from '../models/auth-user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  user = signal<AuthUser | null>(null);

  private readonly URL = '/api/account/oauth2/authorization/github';

  loginWithGithub(): void {
    globalThis.location.href = this.URL
  }

  fetchUser(): Observable<AuthUser> {
    return this.http.get<AuthUser>('/api/account/user/me', {
      withCredentials: true
    });
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }
}