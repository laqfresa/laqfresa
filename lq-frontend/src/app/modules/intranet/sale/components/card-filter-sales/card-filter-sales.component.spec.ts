import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardFilterSalesComponent } from './card-filter-sales.component';

describe('CardFilterSalesComponent', () => {
  let component: CardFilterSalesComponent;
  let fixture: ComponentFixture<CardFilterSalesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardFilterSalesComponent]
    });
    fixture = TestBed.createComponent(CardFilterSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
