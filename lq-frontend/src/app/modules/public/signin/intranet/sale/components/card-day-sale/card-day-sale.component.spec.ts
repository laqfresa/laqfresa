import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardDaySaleComponent } from './card-day-sale.component';

describe('CardDaySaleComponent', () => {
  let component: CardDaySaleComponent;
  let fixture: ComponentFixture<CardDaySaleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardDaySaleComponent]
    });
    fixture = TestBed.createComponent(CardDaySaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
