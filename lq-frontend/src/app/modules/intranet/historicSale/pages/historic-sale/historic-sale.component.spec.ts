import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricSaleComponent } from './historic-sale.component';

describe('HistoricSaleComponent', () => {
  let component: HistoricSaleComponent;
  let fixture: ComponentFixture<HistoricSaleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoricSaleComponent]
    });
    fixture = TestBed.createComponent(HistoricSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
