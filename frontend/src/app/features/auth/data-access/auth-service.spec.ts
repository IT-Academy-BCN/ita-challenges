import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';

import { AuthService } from './auth-service';
import { AuthUser } from '../models/auth-user.model';

const MOCK_USER: AuthUser = {
  username: 'mockUser',
  avatarUrl: 'https://github.com/MockUser.png',
  token: 'token-808',
};

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should start with null user', () => {
    expect(service.user()).toBeNull();
  });

  it('should set and get user correctly', () => {
    service.setUser(MOCK_USER);
    expect(service.user()).toEqual(MOCK_USER);
    expect(service.getUser()).toEqual(MOCK_USER);
  });

  it('should set mock user and redirect to profile when loginWithGithub is called', () => {
    const originalLocation = globalThis.location;
    const locationMock = { href: '' };

    vi.stubGlobal('location', locationMock);

    service.loginWithGithub();

    expect(service.user()).toEqual(MOCK_USER);
    expect(locationMock.href).toBe('/profile');

    vi.stubGlobal('location', originalLocation);
  });

  it('should fetch mock user', async () => {
    const result = await firstValueFrom(service.fetchUser());
    expect(result).toEqual(MOCK_USER);
  });
});