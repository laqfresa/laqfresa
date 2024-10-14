import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardTotalSalesComponent } from './card-total-sales.component';

describe('CardTotalSalesComponent', () => {
  let component: CardTotalSalesComponent;
  let fixture: ComponentFixture<CardTotalSalesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardTotalSalesComponent]
    });
    fixture = TestBed.createComponent(CardTotalSalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
