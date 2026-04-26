import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthPageComponent } from './auth-page';
import { AuthService } from '../../data-access/auth-service';
import { of, throwError } from 'rxjs';
import { vi } from 'vitest';
import { Router } from '@angular/router';

describe('AuthPageComponent', () => {
  let component: AuthPageComponent;
  let fixture: ComponentFixture<AuthPageComponent>;

  let router: Router;
  let navigateSpy: ReturnType<typeof vi.spyOn>;

  const authServiceMock = {
    loginWithGithub: vi.fn(),
    setUser: vi.fn(),
  };

  const MOCK_USER = { 
    username: 'MockUser', 
    avatarUrl: 'https://github.com/MockUser.png' 
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthPageComponent],
      providers: [
        { 
          provide: AuthService, 
          useValue: authServiceMock 
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthPageComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call loginWithGithub on login', () => {
    authServiceMock.loginWithGithub.mockReturnValue(of({}));
    component.login();

    expect(authServiceMock.loginWithGithub).toHaveBeenCalled();
  });

  it('should set user on success', () => {
    authServiceMock.loginWithGithub.mockReturnValue(of(MOCK_USER));
    component.login();

    expect(authServiceMock.setUser).toHaveBeenCalledWith(MOCK_USER);
    expect(component.loading()).toBe(false);
  });

  it('should navigate to /profile on success', () => {
    authServiceMock.loginWithGithub.mockReturnValue(of(MOCK_USER));
    component.login();
    expect(navigateSpy).toHaveBeenCalledWith(['/profile']);
  });

  it('should not call loginWithGithub if already loading', () => {
    authServiceMock.loginWithGithub.mockReturnValue(of(MOCK_USER));
    component.loading.set(true);
    component.login();

    expect(authServiceMock.loginWithGithub).not.toHaveBeenCalled();
    expect(authServiceMock.setUser).not.toHaveBeenCalled();
  });

  it('should set error on failure', () => {
    authServiceMock.loginWithGithub.mockReturnValue(
      throwError(() => new Error('fail'))
    );
    component.login();

    expect(component.error()).toBe(true);
    expect(component.loading()).toBe(false);
  });

  it('should reset error on retry', () => {
    authServiceMock.loginWithGithub.mockReturnValue(
      throwError(() => new Error('fail'))
    );
    component.login();

    authServiceMock.loginWithGithub.mockReturnValue(of(MOCK_USER));
    component.login();

    expect(component.error()).toBe(false);
  });
});