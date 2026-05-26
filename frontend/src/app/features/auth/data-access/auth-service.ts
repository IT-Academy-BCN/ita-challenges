import { inject, Injectable, signal } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthUser } from '../models/auth-user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);

  private readonly githubLoginUrl = '/api/account/oauth2/authorization/github';
  private readonly currentUserUrl = '/api/account/auth/me';
  private readonly logoutUrl = '/api/account/auth/logout';

  user = signal<AuthUser | null>(null);

  loginWithGithub(): void {
    globalThis.location.href = this.githubLoginUrl;
  }

  fetchUser(): Observable<AuthUser> {
    return this.http.get<AuthUser>(this.currentUserUrl);
  }

  setUser(user: AuthUser): void {
    this.user.set(user);
  }

  getUser(): AuthUser | null {
    return this.user();
  }

  logout(): Observable<void> {
    return this.http.post(this.logoutUrl, {}, { responseType: 'text' }).pipe(
      tap(() => {
        this.user.set(null);
      }),
      map(() => { })
    );
  }
}
