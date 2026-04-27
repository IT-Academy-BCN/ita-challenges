import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilePageComponent } from './profile-page';
import { AuthService } from '../../../auth/data-access/auth-service';
import { AuthUser } from '../../../auth/models/auth-user.model';

const MOCK_USER: AuthUser = {
  username: 'MockUser',
  avatarUrl: 'https://github.com/MockUser.png',
};

describe('ProfilePage', () => {
  let component: ProfilePageComponent;
  let fixture: ComponentFixture<ProfilePageComponent>;
  let authService: AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilePageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilePageComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show fallback message when no user is logged in', () => {
    const empty = fixture.nativeElement.querySelector('p');
    expect(empty.textContent).toContain('No user logged in');
  });

  it('should display avatar and username when user is present', () => {
    authService.setUser(MOCK_USER);
    fixture.detectChanges();

    const avatar = fixture.nativeElement.querySelector('img');
    const username = fixture.nativeElement.querySelector('h2');

    expect(avatar.src).toContain(MOCK_USER.avatarUrl);
    expect(username.textContent).toContain(MOCK_USER.username);
  });
});