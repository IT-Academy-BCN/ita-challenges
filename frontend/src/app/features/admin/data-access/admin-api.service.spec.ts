import { TestBed } from '@angular/core/testing';

import { AdminApiService } from './admin-api.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { Role } from '../../../core/models/role.enum';

describe('AdminApiService', () => {
  let service: AdminApiService;
  let httpMock: HttpTestingController;

  const API_URL = 'http://localhost:8080/api/account/users';

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
    const username = 'test';
    const role = Role.MENTOR;
    
    service.setUserRole(username, role).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should send the correct payload', () => {
    const username = 'test';
    const role = Role.MENTOR;

    service.setUserRole(username, role).subscribe();

    const req = httpMock.expectOne(API_URL);
    expect(req.request.body).toEqual({ username, role });
    req.flush(null);
  });

  it('should return an observable that completes on success', () => {
    const username = 'test';
    const role = Role.MENTOR;
    let completed = false;

    service.setUserRole(username, role).subscribe({
      complete: () => (completed = true),
    });

    httpMock.expectOne(API_URL).flush(null);
    expect(completed).toBeTruthy();
  });
});
