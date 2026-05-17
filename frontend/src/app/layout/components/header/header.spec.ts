import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Header } from './header';

describe('Header', () => {

  let component: Header;
  let fixture: ComponentFixture<Header>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [Header]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Header);
    component = fixture.componentInstance;

    await fixture.whenStable();
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render logout button', () => {

    const logoutButton =
      fixture.nativeElement.querySelector('app-logout-button');

    expect(logoutButton).toBeTruthy();

  });

});