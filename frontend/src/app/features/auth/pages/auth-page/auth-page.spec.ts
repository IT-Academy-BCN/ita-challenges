import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthPageComponent } from './auth-page';
import { AuthService } from '../../data-access/auth-service';
import { of, throwError } from 'rxjs';
import { vi } from 'vitest';

describe('AuthPageComponent', () => {
  let component: AuthPageComponent;
  let fixture: ComponentFixture<AuthPageComponent>;

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