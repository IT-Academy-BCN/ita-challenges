import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignRolePage } from './assign-role-page';

describe('AssignRolePage', () => {
  let component: AssignRolePage;
  let fixture: ComponentFixture<AssignRolePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssignRolePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignRolePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
