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
    fixture.detectChanges();
  });

  it('should start as student (isMentor = false)', () => {
    expect(component.isMentor()).toBe(false);
  });

  it('should toggle to mentor when toggle is called', () => {
    component.toggle();
    expect(component.isMentor()).toBe(true);
  });

  it('should toggle back to student on second call', () => {
    component.toggle();
    component.toggle();
    expect(component.isMentor()).toBe(false);
  });

  it('should emit true when toggled to mentor', () => {
    const emitSpy = vi.spyOn(component.roleChange, 'emit');
    component.toggle();
    expect(emitSpy).toHaveBeenCalledWith(true);
  });

  it('should emit false when toggled back to student', () => {
    const emitSpy = vi.spyOn(component.roleChange, 'emit');
    component.toggle();
    component.toggle();
    expect(emitSpy).toHaveBeenCalledWith(false);
  });
});