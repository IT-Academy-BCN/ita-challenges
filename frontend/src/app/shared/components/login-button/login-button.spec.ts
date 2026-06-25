import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginButton } from './login-button';
import { Router } from '@angular/router';

describe('LoginButton', () => {
  let component: LoginButton;
  let fixture: ComponentFixture<LoginButton>;
  let routerMock: {
    navigate: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    routerMock = {
      navigate: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [LoginButton],
      providers: [
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginButton);
    component = fixture.componentInstance;

    await fixture.whenStable();

    fixture.detectChanges();
  });

  it('should call login method and navigate to /auth when button is clicked', () => {
    const spy = vi.spyOn(component, 'login');

    const button: HTMLButtonElement =
      fixture.nativeElement.querySelector('button');

    button.click();

    expect(spy).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/auth']);
  });
});
