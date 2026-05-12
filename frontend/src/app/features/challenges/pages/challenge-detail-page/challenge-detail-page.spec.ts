import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeDetailPage } from './challenge-detail-page';

describe('ChallengeDetailPage', () => {
  let component: ChallengeDetailPage;
  let fixture: ComponentFixture<ChallengeDetailPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengeDetailPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengeDetailPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render challenge data from mock', () => {
    const mockChallenge = {
      id: '1',
      title: 'primer',
      description: 'descripció 1',
    };

    component.challenge = mockChallenge;
    fixture.detectChanges();
    
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain(mockChallenge.title);
    expect(compiled.querySelector('p')?.textContent).toContain(mockChallenge.description);
  });
});
