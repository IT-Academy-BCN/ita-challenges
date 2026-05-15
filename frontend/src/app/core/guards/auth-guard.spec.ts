import { TestBed } from '@angular/core/testing';
import { Router, UrlTree } from '@angular/router';
import { authGuard } from './auth-guard';
import { AuthService } from '../../features/auth/data-access/auth-service';
import { AuthUser } from '../../features/auth/models/auth-user.model';
import { firstValueFrom, throwError } from 'rxjs';

const MOCK_USER: AuthUser = {
  username: 'MockUser',
  avatarUrl: 'https://github.com/MockUser.png',
};

describe('authGuard', () => {
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: AuthService,
          useValue: {
            getUser: vi.fn(),
            setUser: vi.fn(),
            fetchUser: vi.fn(),
          },
        },
        {
          provide: Router,
          useValue: {
            createUrlTree: vi.fn(() => ({} as UrlTree)),
          },
        },
      ],
    });

    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
  });

  it('should allow navigation when user is authenticated', () => {
    vi.mocked(authService.getUser).mockReturnValue(MOCK_USER);

    const result = TestBed.runInInjectionContext(() =>
      authGuard({} as any, {} as any)
    );

    expect(result).toBe(true);
  });

  it('should redirect to /auth when no user is authenticated', async () => {
    vi.mocked(authService.getUser).mockReturnValue(null);

    vi.mocked(authService.fetchUser).mockReturnValue(
      throwError(() => new Error('Unauthorized'))
    );

    const result = await firstValueFrom(
      TestBed.runInInjectionContext(() =>
        authGuard({} as any, {} as any)
      ) as any
    );

    expect(result).toEqual({});
    expect(router.createUrlTree).toHaveBeenCalledWith(['/auth']);
  });
});