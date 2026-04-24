import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';

import { AuthService } from './auth-service';
import { AuthUser } from '../models/auth-user.model';

const mockUser: AuthUser = {
  username: 'JordiMiravet',
  avatarUrl: 'https://github.com/JordiMiravet.png',
};

describe('AuthService', () => {
  let service: AuthService;
  let getUser: () => AuthUser | null;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);

    getUser = () => service.user();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize user as null', () => {
    expect(getUser()).toBeNull();
  });

  it('should return current user when getUser is called', () => {
    service.setUser(mockUser);
    expect(getUser()).toEqual(mockUser);
  });

  it('should return mock user when loginWithGithub is called', async () => {
    const result = await firstValueFrom(service.loginWithGithub());
    expect(result).toEqual(mockUser);
  });

  it('should not modify user signal when loginWithGithub is called', () => {
    service.loginWithGithub().subscribe();
    expect(getUser()).toBeNull();
  });
});