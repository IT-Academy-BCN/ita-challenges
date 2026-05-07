import { TestBed } from '@angular/core/testing';
import { Router, UrlTree } from '@angular/router';
import { authGuard } from './auth-guard';
import { AuthService } from '../../features/auth/data-access/auth-service';
import { AuthUser } from '../../features/auth/models/auth-user.model';

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
        AuthService,
        {
          provide: Router,
          useValue: {
            navigate: vi.fn(),
            createUrlTree: vi.fn(() => ({} as UrlTree)),
          },
        },
      ],
    });

    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
  });

  it('should allow navigation when user is authenticated', () => {
    authService.setUser(MOCK_USER);

    const result = TestBed.runInInjectionContext(() =>
      authGuard({} as any, {} as any)
    );

    expect(result).toBe(true);
  });

  it('should redirect to /auth when no user is authenticated', () => {
    const result = TestBed.runInInjectionContext(() =>
      authGuard({} as any, {} as any)
    );
    
    expect(result).toEqual({});
    expect(router.createUrlTree).toHaveBeenCalledWith(['/auth']);
  });
});