import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilePageComponent } from './profile-page';
import { AuthService } from '../../../auth/data-access/auth-service';
import { AuthUser } from '../../../auth/models/auth-user.model';
import { signal } from '@angular/core';
import { of, throwError } from 'rxjs';
import { vi } from 'vitest';

const MOCK_USER: AuthUser = {
  username: 'MockUser',
  avatarUrl: 'https://github.com/MockUser.png',
  token: 'token-808',
};

describe('ProfilePage', () => {
  let component: ProfilePageComponent;
  let fixture: ComponentFixture<ProfilePageComponent>;

  const userSignal = signal<AuthUser | null>(null);

  const authServiceMock = {
    user: userSignal,
    fetchUser: vi.fn(),
    setUser: vi.fn((user: AuthUser) => userSignal.set(user)),
  };

  beforeEach(async () => {
    authServiceMock.fetchUser.mockReset();
    authServiceMock.setUser.mockReset();
    userSignal.set(null);

    await TestBed.configureTestingModule({
      imports: [ProfilePageComponent],
      providers: [
        {
          provide: AuthService,
          useValue: authServiceMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilePageComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));
    fixture.detectChanges();

    expect(component).toBeTruthy();
  });

  it('should call setUser when fetchUser succeeds', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));
    fixture.detectChanges();

    expect(authServiceMock.setUser).toHaveBeenCalledWith(MOCK_USER);
  });

  it('should set error when fetchUser fails', () => {
    authServiceMock.fetchUser.mockReturnValue(
      throwError(() => new Error('fail'))
    );
    fixture.detectChanges();

    expect(component.error()).toBe(true);
  });
});