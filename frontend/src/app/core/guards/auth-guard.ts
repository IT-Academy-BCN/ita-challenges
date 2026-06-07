import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../features/auth/data-access/auth-service';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.getUser()) {
      return true;
    }

 return auth.fetchUser().pipe(
     map((user) => {
       auth.setUser(user);
       return true;
     }),
     catchError(() => {
       return of(router.createUrlTree(['/']));
     })
   );
 };
