import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Header } from './header';
import { signal } from '@angular/core';
import { AuthUser } from '../../../features/auth/models/auth-user.model';
import { of } from 'rxjs';
import { AuthService } from '../../../features/auth/data-access/auth-service';
import { Role } from '../../../core/models/role.enum';
import { Router } from '@angular/router';

const MOCK_USER: AuthUser = {
  username: 'mockUser',
  avatarUrl: 'https://github.com/MockUser.png',
  token: 'token-808',
  role: Role.GUEST,
};

describe('Header', () => {
  let component: Header;
  let fixture: ComponentFixture<Header>;
  let authServiceMock: {
    user: ReturnType<typeof signal<AuthUser | null>>;
    logout: ReturnType<typeof vi.fn>;
    fetchUser: ReturnType<typeof vi.fn>;
    getUser: ReturnType<typeof vi.fn>;
    setUser: ReturnType<typeof vi.fn>;
  };
  let routerMock: {
    navigate: ReturnType<typeof vi.fn>;
  };
  beforeEach(async () => {
    authServiceMock = {
      user: signal(null),
      logout: vi.fn().mockReturnValue(of(void 0)),
      fetchUser: vi.fn().mockReturnValue(of(null)),
      getUser: vi.fn().mockReturnValue(null),
      setUser: vi.fn(),
    };
    routerMock = {
      navigate: vi.fn(),
    };
    await TestBed.configureTestingModule({
      imports: [Header],
      providers: [{ provide: AuthService, useValue: authServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(Header);
    component = fixture.componentInstance;

    await fixture.whenStable();

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show logout button when user is logged in', () => {
    authServiceMock.user.set(MOCK_USER);
    fixture.detectChanges();

    const logoutButton = fixture.nativeElement.querySelector('app-logout-button');

    expect(logoutButton).toBeTruthy();
  });

  it('should not show logout button when user is not logged in', () => {
    authServiceMock.user.set(null);
    fixture.detectChanges();

    const logoutButton = fixture.nativeElement.querySelector('app-logout-button');

    expect(logoutButton).toBeFalsy();
  });

  it('should show username when user is logged in', () => {
    authServiceMock.user.set(MOCK_USER);
    fixture.detectChanges();

    const h4s = fixture.nativeElement.querySelectorAll('h4');
    const usernameEl = Array.from(h4s).find((el: any) =>
      el.textContent.includes(MOCK_USER.username),
    );
    expect(usernameEl).toBeTruthy();
  });

  it('should not show username when user is not logged in', () => {
    authServiceMock.user.set(null);
    fixture.detectChanges();

    const usernameDiv = fixture.nativeElement.querySelector('.user__username');
    expect(usernameDiv).toBeFalsy();
  });

  it('should show INVITAT and Log In button when user is not logged in', () => {
    authServiceMock.user.set(null);
    fixture.detectChanges();

    const h4 = fixture.nativeElement.querySelector('h4');
    const loginButton = fixture.nativeElement.querySelector('.button__login button');

    expect(h4.textContent).toContain('INVITAT');
    expect(loginButton).toBeTruthy();
    expect(loginButton.textContent).toContain('Log In');
  });

  it('should show user avatar when user is logged in', () => {
    authServiceMock.user.set(MOCK_USER);
    fixture.detectChanges();

    const avatar = fixture.nativeElement.querySelector('.avatar__image');
    expect(avatar).toBeTruthy();
    expect(avatar.src).toContain(MOCK_USER.avatarUrl);
  });

  it('should not show user avatar when user is not logged in', () => {
    authServiceMock.user.set(null);
    fixture.detectChanges();

    const avatar = fixture.nativeElement.querySelector('.avatar__image');
    expect(avatar).toBeFalsy();
  });

  it('should show user role when user has a role', () => {
    authServiceMock.user.set({ ...MOCK_USER, role: Role.GUEST });
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('h4').textContent).toContain(Role.GUEST);
  });

  it('should show CONVIDAT when user has no role', () => {
    authServiceMock.user.set({ ...MOCK_USER, role: undefined });
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('h4').textContent).toContain('CONVIDAT');
  });

  it('should redirect to challenges after fetching authenticated user', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));

    component.ngOnInit();

    expect(authServiceMock.setUser).toHaveBeenCalledWith(MOCK_USER);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/challenges']);
  });

  it('should not redirect when user already exists', () => {
    authServiceMock.getUser.mockReturnValue(MOCK_USER);

    component.ngOnInit();

    expect(authServiceMock.fetchUser).not.toHaveBeenCalled();
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });
});
