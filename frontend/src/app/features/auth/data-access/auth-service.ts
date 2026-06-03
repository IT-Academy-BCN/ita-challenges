import { inject, Injectable, signal } from '@angular/core';
import { catchError, map, Observable, of, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthUser } from '../models/auth-user.model';
import { Role } from '../../../core/models/role.enum';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly githubLoginUrl = '/api/account/oauth2/authorization/github';
  private readonly logoutUrl = '/api/account/auth/logout';
  private readonly userRoleUrl = (username: string) => `/api/account/users/${username}/role`;

  user = signal<AuthUser | null>(null);

  loginWithGithub(): void {
    globalThis.location.href = this.githubLoginUrl;
  }

  private getUserRole(username: string): Observable<Role> {
    return this.http.get<{ role: Role }>(this.userRoleUrl(username))
      .pipe(map(response => response.role));
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
