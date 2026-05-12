import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AssignRolePage } from './assign-role-page';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminApiService } from '../../data-access/admin-api.service';
import { Role } from '../../../../core/models/role.enum';
import { of } from 'rxjs';

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
    it('should have role options', () => {
      expect(component.roleOptions.length).toBeGreaterThan(0);
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
 
    it('should reset the form after success', () => {
      component.onSubmit();
      expect(component.roleForm.value).toEqual({ username: null, role: null });
    });
 
  });  

});
