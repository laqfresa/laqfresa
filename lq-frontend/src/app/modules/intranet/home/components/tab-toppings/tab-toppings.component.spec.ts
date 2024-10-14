import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabToppingsComponent } from './tab-toppings.component';

describe('TabToppingsComponent', () => {
  let component: TabToppingsComponent;
  let fixture: ComponentFixture<TabToppingsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabToppingsComponent]
    });
    fixture = TestBed.createComponent(TabToppingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
