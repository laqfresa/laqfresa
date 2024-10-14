import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeIntranetComponent } from './home-intranet.component';

describe('HomeIntranetComponent', () => {
  let component: HomeIntranetComponent;
  let fixture: ComponentFixture<HomeIntranetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeIntranetComponent]
    });
    fixture = TestBed.createComponent(HomeIntranetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
