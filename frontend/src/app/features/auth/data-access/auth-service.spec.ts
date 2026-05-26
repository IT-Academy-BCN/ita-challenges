import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { vi } from 'vitest';

import { AuthService } from './auth-service';
import { AuthUser } from '../models/auth-user.model';

const MOCK_USER: AuthUser = {
  username: 'mockUser',
  avatarUrl: 'https://github.com/MockUser.png',
  token: 'token-808',
};

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
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

  it('should redirect to github when loginWithGithub is called', () => {
    const locationMock = { href: '' };
    vi.stubGlobal('location', locationMock);
    service.loginWithGithub();

    expect(locationMock.href).toBe('/api/account/oauth2/authorization/github');

    vi.unstubAllGlobals();
  });

  it('should not modify user signal when loginWithGithub is called', () => {
    const locationMock = { href: '' };
    vi.stubGlobal('location', locationMock);
    service.loginWithGithub();

    expect(service.user()).toBeNull();

    vi.unstubAllGlobals();
  });

  it('should fetch user from API', async () => {
    const promise = firstValueFrom(service.fetchUser());

    const req = httpMock.expectOne('/api/account/auth/me');
    expect(req.request.method).toBe('GET');
    req.flush(MOCK_USER);

    const result = await promise;
    expect(result).toEqual(MOCK_USER);
  });

  it('should clear current user on logout', async () => {
    service.setUser(MOCK_USER);

    const promise = firstValueFrom(service.logout());

    const req = httpMock.expectOne('/api/account/auth/logout');
    expect(req.request.method).toBe('POST');
    req.flush('');

    await promise;

    expect(service.user()).toBeNull();
  });
});
