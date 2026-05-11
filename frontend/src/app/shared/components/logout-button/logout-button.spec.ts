import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoutButton } from './logout-button';
import { AuthService } from '../../../features/auth/data-access/auth-service';

describe('LogoutButton', () => {

  let component: LogoutButton;
  let fixture: ComponentFixture<LogoutButton>;

  let authServiceMock: {
    logout: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {

    authServiceMock = {
      logout: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [LogoutButton],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogoutButton);
    component = fixture.componentInstance;

    await fixture.whenStable();

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call logout method when button is clicked', () => {

    const spy = vi.spyOn(component, 'logout');

    const button: HTMLButtonElement =
      fixture.nativeElement.querySelector('button');

    button.click();

    expect(spy).toHaveBeenCalled();

  });

  it('should call AuthService.logout', () => {

    component.logout();

    expect(authServiceMock.logout).toHaveBeenCalled();

  });

});
