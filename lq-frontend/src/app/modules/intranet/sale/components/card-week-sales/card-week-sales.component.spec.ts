import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardWeekSalesComponent } from './card-week-sales.component';

describe('CardWeekSalesComponent', () => {
  let component: CardWeekSalesComponent;
  let fixture: ComponentFixture<CardWeekSalesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardWeekSalesComponent]
    });
    fixture = TestBed.createComponent(CardWeekSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
