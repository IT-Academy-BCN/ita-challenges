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

  it('should have default role as student', () => {
    expect(service.role()).toBe('student');
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

  it('should reflect isMentor back to false when role changes to student', () => {
    service.setRole('mentor');
    service.setRole('student');

    expect(service.isMentor()).toBeFalsy();
    expect(service.role()).toBe('student');
  });

  it('should keep role reactive across multiple updates', () => {
    const values: Role[] = [];

    values.push(service.role());

    service.setRole('mentor');
    values.push(service.role());

    service.setRole('student');
    values.push(service.role());

    expect(values).toEqual(['student', 'mentor', 'student']);
  });

  it('should keep computed isMentor reactive', () => {
    const states: boolean[] = [];

    states.push(service.isMentor());

    service.setRole('mentor');
    states.push(service.isMentor());

    service.setRole('student');
    states.push(service.isMentor());

    expect(states).toEqual([false, true, false]);
  });
});