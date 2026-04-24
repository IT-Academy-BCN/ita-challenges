import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateChallengePage } from './create-challenge-page';

describe('CreateChallengePage', () => {
  let component: CreateChallengePage;
  let fixture: ComponentFixture<CreateChallengePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateChallengePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateChallengePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should start the form with empty fields', () => {
    const title = component.challengeForm.get('title')
    const description = component.challengeForm.get('description')

    expect(title?.value).toBe('')
    expect(description?.value).toBe('')
  })

it('should call onSubmit when form is submitted', () => {
  vi.spyOn(component, 'onSubmit');

  const form = fixture.nativeElement.querySelector('form');
  form.dispatchEvent(new Event('submit'));

  expect(component.onSubmit).toHaveBeenCalled();
});
});
