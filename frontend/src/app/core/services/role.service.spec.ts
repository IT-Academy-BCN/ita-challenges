import { TestBed } from '@angular/core/testing';
import { RoleService } from './role.service';
import { Role } from '../models/role.model';

describe('RoleService', () => {
  let service: RoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should have default role as guest', () => {
    expect(service.role()).toBe('guest');
  });

  it('should update role when setRole is called', () => {
    service.setRole('mentor');
    expect(service.role()).toBe('mentor');
  });

  it('should reflect isMentor as false by default', () => {
    expect(service.isMentor()).toBeFalsy();
  });

  it('should reflect isMentor as true when role is mentor', () => {
    service.setRole('mentor');
    expect(service.isMentor()).toBe(true);
  });

  it('should reflect isMentor back to false when role changes to guest', () => {
    service.setRole('mentor');
    service.setRole('guest');

    expect(service.isMentor()).toBeFalsy();
    expect(service.role()).toBe('guest');
  });

  it('should reflect isStudent correctly', () => {
    expect(service.isStudent()).toBeFalsy();
    service.setRole('student');
    expect(service.isStudent()).toBe(true);
  });

  it('should reflect isGuest correctly', () => {
    expect(service.isGuest()).toBe(true);
    service.setRole('mentor');
    expect(service.isGuest()).toBe(false);
  });

});