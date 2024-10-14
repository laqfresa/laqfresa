import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEditComboComponent } from './modal-edit-combo.component';

describe('ModalEditComboComponent', () => {
  let component: ModalEditComboComponent;
  let fixture: ComponentFixture<ModalEditComboComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalEditComboComponent]
    });
    fixture = TestBed.createComponent(ModalEditComboComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
