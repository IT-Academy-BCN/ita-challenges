import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth-service';
import { AuthUser } from '../models/auth-user.model';

const mockUser: AuthUser = {
  username: 'JordiMiravet',
  avatarUrl: 'url-123',
};

describe.only('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize user as null', () => {
    expect(service.user()).toBe(null)
  });

  it('should set user when setUser is called', () => {
      service.setUser(mockUser);

      expect(service.user()).toEqual(mockUser);
  });

  it('should return current user when getUser is called', () => {
    service.setUser(mockUser);

    expect(service.getUser()).toEqual(mockUser);
  });

  it('should not emit any value from loginWithGithub', () => {
    let emitted = false;

    service.loginWithGithub().subscribe({
      next: () => {
        emitted = true;
      },
    });

    expect(emitted).toBe(false);
  });
  
});