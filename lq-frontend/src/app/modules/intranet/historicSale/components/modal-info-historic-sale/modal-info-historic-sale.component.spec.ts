import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModalInfoHistoricSaleComponent } from './modal-info-historic-sale.component';

describe('ModalInfoUsersComponent', () => {
  let component: ModalInfoHistoricSaleComponent;
  let fixture: ComponentFixture<ModalInfoHistoricSaleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalInfoHistoricSaleComponent]
    });
    fixture = TestBed.createComponent(ModalInfoHistoricSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
