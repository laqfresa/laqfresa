import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardMonthSalesComponent } from './card-month-sales.component';

describe('CardMonthSalesComponent', () => {
  let component: CardMonthSalesComponent;
  let fixture: ComponentFixture<CardMonthSalesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardMonthSalesComponent]
    });
    fixture = TestBed.createComponent(CardMonthSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
