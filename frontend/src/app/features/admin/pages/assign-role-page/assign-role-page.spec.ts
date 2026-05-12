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
  });  
  describe('onSubmit() — error path', () => {
    beforeEach(() => {
      component.roleForm.controls.username.setValue('user123');
      component.roleForm.controls.role.setValue(Role.MENTOR);
      adminApiServiceMock.setUserRole.mockReturnValue(
        throwError(() => new Error('Server error'))
      );
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
