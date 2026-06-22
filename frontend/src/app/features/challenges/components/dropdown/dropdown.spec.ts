import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DropdownComponent } from './dropdown';

describe('DropdownComponent', () => {
  let component: DropdownComponent;
  let fixture: ComponentFixture<DropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropdownComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be closed by default', () => {
    expect(component.isOpen()).toBeFalsy();

    const menu = fixture.debugElement.query(By.css('.dropdown__menu'));
    const overlay = fixture.debugElement.query(By.css('.dropdown__overlay'));

    expect(menu).toBeNull();
    expect(overlay).toBeNull();
  });

  it('should open dropdown when trigger is clicked', () => {
    const button = fixture.debugElement.query(By.css('.dropdown__trigger'));

    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.isOpen()).toBeTruthy();

    const menu = fixture.debugElement.query(By.css('.dropdown__menu'));
    const overlay = fixture.debugElement.query(By.css('.dropdown__overlay'));

    expect(menu).not.toBeNull();
    expect(overlay).not.toBeNull();
  });

  it('should close dropdown when trigger is clicked twice', () => {
    const button = fixture.debugElement.query(By.css('.dropdown__trigger'));

    button.nativeElement.click();
    fixture.detectChanges();

    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.isOpen()).toBeFalsy();

    const menu = fixture.debugElement.query(By.css('.dropdown__menu'));
    expect(menu).toBeNull();
  });

  it('should close dropdown when overlay is clicked', () => {
    const button = fixture.debugElement.query(By.css('.dropdown__trigger'));

    button.nativeElement.click();
    fixture.detectChanges();

    const overlay = fixture.debugElement.query(By.css('.dropdown__overlay'));
    overlay.nativeElement.click();
    fixture.detectChanges();

    expect(component.isOpen()).toBeFalsy();

    const menu = fixture.debugElement.query(By.css('.dropdown__menu'));
    expect(menu).toBeNull();
  });
});
