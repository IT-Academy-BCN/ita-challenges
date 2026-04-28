import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthPageComponent } from './auth-page';
import { AuthService } from '../../data-access/auth-service';
import { vi } from 'vitest';

describe('AuthPageComponent', () => {
  let component: AuthPageComponent;
  let fixture: ComponentFixture<AuthPageComponent>;

  const authServiceMock = {
    loginWithGithub: vi.fn(),
  };

  beforeEach(async () => {
    authServiceMock.loginWithGithub.mockReset();

    await TestBed.configureTestingModule({
      imports: [AuthPageComponent],
      providers: [
        {
          provide: AuthService,
          useValue: authServiceMock,
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

  it('should call loginWithGithub on login', async () => {
    component.login();
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(authServiceMock.loginWithGithub).toHaveBeenCalled();
  });

  it('should set loading to true when login is called', () => {
    component.login();
    expect(component.loading()).toBe(true);
  });
});