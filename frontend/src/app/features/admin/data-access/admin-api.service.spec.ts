import { TestBed } from '@angular/core/testing';

import { AdminApiService } from './admin-api.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { Role } from '../../../core/models/role.enum';
import { IUser } from '../../../shared/models/iuser.interface';

describe('AdminApiService', () => {
  let service: AdminApiService;
  let httpMock: HttpTestingController;

  const API_URL = 'http://localhost:8080/api/account/auth/register';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(AdminApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should POST to the correct URL', () => {
    const user: IUser= {username: 'test', role: Role.MENTOR}
   
    service.setUserRole(user.username, user.role).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('POST');
    req.flush(user);
  });

  it('should send the correct payload', () => {
    const user: IUser= {username: 'test', role: Role.MENTOR}

    service.setUserRole(user.username, user.role).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.body).toEqual(user);
    req.flush(user);
  });

  it('should return an observable that completes on success', () => {
    const user: IUser= {username: 'test', role: Role.MENTOR}
    let completed = false;

    service.setUserRole(user.username, user.role).subscribe({
      complete: () => (completed = true),
    });

    httpMock.expectOne(API_URL).flush(user);
    expect(completed).toBeTruthy();
  });
});
