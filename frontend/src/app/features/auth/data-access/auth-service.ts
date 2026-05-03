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


  loginWithGithub(): void {
    globalThis.location.href = '/api/account/oauth2/authorization/github';
  }

  fetchUser(): Observable<AuthUser> {
    return this.http.get<AuthUser>('/api/account/auth/me');
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }
}