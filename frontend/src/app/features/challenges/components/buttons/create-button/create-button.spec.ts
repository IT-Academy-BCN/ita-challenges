import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateButtonComponent } from './create-button';
import { provideRouter } from '@angular/router';
import { RouterLink } from '@angular/router';
import { By } from '@angular/platform-browser';

describe('CreateButtonComponent', () => {
  let component: CreateButtonComponent;
  let fixture: ComponentFixture<CreateButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateButtonComponent],
      providers: [provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render a button', () => {
    const button = fixture.nativeElement.querySelector('button');
    expect(button).toBeTruthy();
  });

  it('should render button with text "Create Challenge"', () => {
    const button = fixture.nativeElement.querySelector('button');
    expect(button.textContent.trim()).toBe('Create Challenge');
  });

  it('should have routerLink to /challenges/create', () => {
    const button = fixture.nativeElement.querySelector('button');
    
    expect(button).toBeTruthy();
    expect(button.getAttribute('routerLink')).toBe('/challenges/create');
  });
});