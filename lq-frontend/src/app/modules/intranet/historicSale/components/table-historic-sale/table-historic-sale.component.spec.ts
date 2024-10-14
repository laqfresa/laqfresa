import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableHistoricSaleComponent } from './table-historic-sale.component';

describe('TableProductsComponent', () => {
  let component: TableHistoricSaleComponent;
  let fixture: ComponentFixture<TableHistoricSaleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableHistoricSaleComponent]
    });
    fixture = TestBed.createComponent(TableHistoricSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
