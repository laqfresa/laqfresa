import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInfoProductComponent } from './modal-info-product.component';

describe('ModalInfoProductComponent', () => {
  let component: ModalInfoProductComponent;
  let fixture: ComponentFixture<ModalInfoProductComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalInfoProductComponent]
    });
    fixture = TestBed.createComponent(ModalInfoProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
