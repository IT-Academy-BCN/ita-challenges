import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { AuthService } from '../../features/auth/data-access/auth-service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const user = auth.getUser();

  return user
    ? true
    : auth.fetchUser().pipe(
        map(user => {
          auth.setUser(user);
          return true;
        }),
        catchError(() => of(router.createUrlTree(['/auth'])))
      );
};