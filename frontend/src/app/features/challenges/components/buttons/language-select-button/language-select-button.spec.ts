import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ComponentRef } from '@angular/core';
import { LanguageSelectButton } from './language-select-button';

describe('LanguageSelectButton', () => {
  let component: LanguageSelectButton;
  let fixture: ComponentFixture<LanguageSelectButton>;
  let componentRef: ComponentRef<LanguageSelectButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LanguageSelectButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageSelectButton);
    component = fixture.componentInstance;
    componentRef = fixture.componentRef;
    componentRef.setInput('language', 'JAVA');
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render icon, label and active class', () => {
    componentRef.setInput('isActive', true);
    fixture.detectChanges();
    const btn = fixture.nativeElement.querySelector('button');

    expect(btn.querySelector('img').src).toContain('assets/Java.svg');
    expect(btn.textContent.trim()).toBe('Java');
    expect(btn.classList).toContain('language-btn-active');
  });

  it('should emit clicked with language on button click', () => {
    const emittedValues: string[] = [];
    component.clicked.subscribe((val) => emittedValues.push(val));
    fixture.nativeElement.querySelector('button').click();
    expect(emittedValues).toEqual(['JAVA']);
  });
});
