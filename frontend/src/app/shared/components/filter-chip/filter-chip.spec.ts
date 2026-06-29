import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FilterChipComponent } from './filter-chip';

describe('FilterChipComponent', () => {
  let fixture: ComponentFixture<FilterChipComponent>;

  const getButton = (): HTMLButtonElement =>
    fixture.nativeElement.querySelector('[data-testid="filter-chip-button"]');

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilterChipComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FilterChipComponent);
  });

  it('should render the label', () => {
    fixture.componentRef.setInput('label', 'Java');
    fixture.detectChanges();
    expect(getButton().textContent?.trim()).toBe('Java');
  });

  it('should not have active class by default', () => {
    fixture.componentRef.setInput('label', 'Java');
    fixture.detectChanges();
    expect(getButton().classList).not.toContain('filter-chip--active');
  });

  it('should apply active class when active is true', () => {
    fixture.componentRef.setInput('label', 'Java');
    fixture.componentRef.setInput('active', true);
    fixture.detectChanges();
    expect(getButton().classList).toContain('filter-chip--active');
  });

  it('should emit clicked when button is clicked', () => {
    fixture.componentRef.setInput('label', 'Java');
    fixture.detectChanges();

    let emitted = false;
    fixture.componentInstance.clicked.subscribe(() => (emitted = true));

    getButton().click();
    expect(emitted).toBe(true);
  });
});
