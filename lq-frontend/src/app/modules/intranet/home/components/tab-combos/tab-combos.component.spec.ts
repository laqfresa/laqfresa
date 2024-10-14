import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabCombosComponent } from './tab-combos.component';

describe('TabCombosComponent', () => {
  let component: TabCombosComponent;
  let fixture: ComponentFixture<TabCombosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabCombosComponent]
    });
    fixture = TestBed.createComponent(TabCombosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
