import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageSelectButton } from './language-select-button';

describe('LanguageSelectButton', () => {
  let component: LanguageSelectButton;
  let fixture: ComponentFixture<LanguageSelectButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LanguageSelectButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageSelectButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
