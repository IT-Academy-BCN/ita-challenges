import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignRolePage } from './assign-role-page';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminApiService } from '../../data-access/admin-api.service';
import { Role } from '../../../../core/models/role.enum';
import { of, throwError } from 'rxjs';

describe('AssignRolePage', () => {
  let component: AssignRolePage;
  let fixture: ComponentFixture<AssignRolePage>;
  let adminApiServiceMock: { setUserRole: ReturnType<typeof vi.fn> };

  beforeEach(async () => {
    adminApiServiceMock = {
      setUserRole: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [AssignRolePage, ReactiveFormsModule],
      providers: [
        { provide: AdminApiService, useValue: adminApiServiceMock },
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignRolePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

 describe('Initialization', () => {
 
    it('should initialize the form with empty fields', () => {
      expect(component.roleForm.value).toEqual({ username: '', role: '' });
    });
 
    it('should have multiple role options', () => {
      expect(component.roleOptions.length).toBeGreaterThan(1);
      const roleValues = component.roleOptions.map(opt => opt.value);
      expect(roleValues).toContain(Role.MENTOR);
      expect(roleValues).toContain(Role.STUDENT);
    });
 
    it('should set isSubmitting to false initially', () => {
      expect(component.isSubmitting()).toBe(false);
    });
 
    it('should set submitError to null initially', () => {
      expect(component.submitError()).toBeNull();
    });
  });

  describe('Form validation', () => {
    describe('username', () => {
      it('should be invalid when empty', () => {
        component.roleForm.controls.username.setValue('');
        expect(component.roleForm.controls.username.invalid).toBe(true);
      });
 
      it('should be invalid with special characters (@)', () => {
        component.roleForm.controls.username.setValue('user@name');
        expect(component.roleForm.controls.username.invalid).toBe(true);
      });
 
      it('should be valid with letters, numbers and hyphens', () => {
        component.roleForm.controls.username.setValue('User-123');
        expect(component.roleForm.controls.username.valid).toBe(true);
      });
    });
 
    describe('role', () => {
      it('should be invalid when no value is selected', () => {
        component.roleForm.controls.role.setValue('');
        expect(component.roleForm.controls.role.invalid).toBe(true);
      });
 
      it('should be valid with a valid role value', () => {
        component.roleForm.controls.role.setValue(Role.MENTOR);
        expect(component.roleForm.controls.role.valid).toBe(true);
      });
    });
 
  });

  describe('onSubmit() — happy path', () => {
    beforeEach(() => {
      component.roleForm.controls.username.setValue('user123');
      component.roleForm.controls.role.setValue(Role.MENTOR);
      adminApiServiceMock.setUserRole.mockReturnValue(of(void 0));
    });
 
    it('should call setUserRole with the correct username and role', () => {
      component.onSubmit();
      expect(adminApiServiceMock.setUserRole).toHaveBeenCalledWith('user123', Role.MENTOR);
    });
 
    it('should set isSubmitting to false after success', () => {
      component.onSubmit();
      expect(component.isSubmitting()).toBe(false);
    });
 
    it('should reset the form after success', () => {
      component.onSubmit();
      expect(component.roleForm.value).toEqual({ username: null, role: null });
    });
 
    it('should set isSubmitting to true before the request is made', () => {
      let isSubmittingDuringCall = false;
      adminApiServiceMock.setUserRole.mockImplementation(() => {
        isSubmittingDuringCall = component.isSubmitting();
        return of(void 0);
      });
      component.onSubmit();
      expect(isSubmittingDuringCall).toBe(true);
    });
  });  

  describe('onSubmit() — error path', () => {
    beforeEach(() => {
      component.roleForm.controls.username.setValue('user123');
      component.roleForm.controls.role.setValue(Role.MENTOR);
      adminApiServiceMock.setUserRole.mockReturnValue(
        throwError(() => new Error('Server error'))
      );
    });
 
    it('should set isSubmitting to false on error', () => {
      component.onSubmit();
      expect(component.isSubmitting()).toBe(false);
    });
 
    it('should set the error message in submitError', () => {
      component.onSubmit();
      expect(component.submitError()).not.toBeNull();
    });
 
    it('should NOT reset the form on error', () => {
      component.onSubmit();
      expect(component.roleForm.controls.username.value).toBe('user123');
    });
  });

  describe('onSubmit() — invalid form', () => {
    it('should NOT call the service when the form is invalid', () => {
      component.roleForm.controls.username.setValue('');
      component.onSubmit();
      expect(adminApiServiceMock.setUserRole).not.toHaveBeenCalled();
    });
  });

});