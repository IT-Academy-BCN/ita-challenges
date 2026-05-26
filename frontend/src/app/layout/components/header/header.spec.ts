import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Header } from './header';
import { signal } from '@angular/core';
import { AuthUser } from '../../../features/auth/models/auth-user.model';
import { of } from 'rxjs';
import { AuthService } from '../../../features/auth/data-access/auth-service';

const MOCK_USER: AuthUser = {
  username: 'mockUser',
  avatarUrl: 'https://github.com/MockUser.png',
  token: 'token-808',
};

describe('Header', () => {

  let component: Header;
  let fixture: ComponentFixture<Header>;
  let authServiceMock: {
    user: ReturnType<typeof signal<AuthUser | null>>;
    logout: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    authServiceMock = {
      user: signal(null),
      logout: vi.fn().mockReturnValue(of(void 0)),
    };

    await TestBed.configureTestingModule({
      imports: [Header],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(Header);
    component = fixture.componentInstance;

    await fixture.whenStable();

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render logout button', () => {

    const logoutButton =
      fixture.nativeElement.querySelector('app-logout-button');

    expect(logoutButton).toBeTruthy();

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
});
