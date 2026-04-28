import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilePageComponent } from './profile-page';
import { AuthService } from '../../../auth/data-access/auth-service';
import { AuthUser } from '../../../auth/models/auth-user.model';
import { of, throwError } from 'rxjs';
import { vi } from 'vitest';

const MOCK_USER: AuthUser = {
  username: 'MockUser',
  avatarUrl: 'https://github.com/MockUser.png',
};

describe('ProfilePage', () => {
  let component: ProfilePageComponent;
  let fixture: ComponentFixture<ProfilePageComponent>;

  const authServiceMock = {
    user: vi.fn().mockReturnValue(null),
    fetchUser: vi.fn(),
    setUser: vi.fn(),
  };

  beforeEach(async () => {
    authServiceMock.fetchUser.mockReset();
    authServiceMock.setUser.mockReset();
    authServiceMock.user.mockReturnValue(null);

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

  it('should show fallback message when no user is logged in', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));
    fixture.detectChanges();

    authServiceMock.user.mockReturnValue(null);
    fixture.detectChanges();

    const p = fixture.nativeElement.querySelector('p.profile__empty');
    expect(p.textContent).toContain('No user logged in');
  });

  it('should display avatar and username when user is present', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));
    authServiceMock.user.mockReturnValue(MOCK_USER);
    fixture.detectChanges();

    const img = fixture.nativeElement.querySelector('img');
    const h2 = fixture.nativeElement.querySelector('h2');

    expect(img.src).toContain(MOCK_USER.avatarUrl);
    expect(h2.textContent).toContain(MOCK_USER.username);
  });

  it('should set error signal when fetchUser fails', () => {
    authServiceMock.fetchUser.mockReturnValue(
      throwError(() => new Error('fail'))
    );
    fixture.detectChanges();

    expect(component.error()).toBe(true);
  });

  it('should call setUser with fetched user on success', () => {
    authServiceMock.fetchUser.mockReturnValue(of(MOCK_USER));
    fixture.detectChanges();

    expect(authServiceMock.setUser).toHaveBeenCalledWith(MOCK_USER);
  });
});