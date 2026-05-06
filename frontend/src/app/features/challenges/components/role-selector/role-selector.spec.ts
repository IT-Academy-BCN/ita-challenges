import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RoleSelectorComponent } from './role-selector';
import { By } from '@angular/platform-browser';

describe('RoleSelectorComponent', () => {
  let component: RoleSelectorComponent;
  let fixture: ComponentFixture<RoleSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleSelectorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RoleSelectorComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should start with student role', () => {
    expect(component.currentRole()).toBe('student');
  });

  it('should toggle role when toggle is called', () => {
    component.toggle();
    expect(component.currentRole()).toBe('mentor');
  });

  it('should toggle back to student when called twice', () => {
    component.toggle();
    component.toggle();
    expect(component.currentRole()).toBe('student');
  });

  it('should toggle role when button is clicked', () => {
    const button = fixture.debugElement.query(
      By.css('.role-selector__switch')
    );
    
    button.triggerEventHandler('click', null);
    fixture.detectChanges();

    expect(component.currentRole()).toBe('mentor');
  });
});