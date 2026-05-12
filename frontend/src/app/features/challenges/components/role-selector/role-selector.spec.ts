import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RoleSelectorComponent } from './role-selector';

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

  it('should start with student role', () => {
    expect(component.currentRole()).toBe('student');
  });

  it('should toggle role when toggle is called', () => {
    component.toggle();
    expect(component.currentRole()).toBe('mentor');
  });
});