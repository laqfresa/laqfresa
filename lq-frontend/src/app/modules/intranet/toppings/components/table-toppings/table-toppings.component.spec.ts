import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableToppingsComponent } from './table-toppings.component';

describe('TableToppingsComponent', () => {
  let component: TableToppingsComponent;
  let fixture: ComponentFixture<TableToppingsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableToppingsComponent]
    });
    fixture = TestBed.createComponent(TableToppingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
