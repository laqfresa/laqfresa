import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEditTypeIngredientComponent } from './modal-edit-type-ingredient.component';

describe('ModalEditTypeIngredientComponent', () => {
  let component: ModalEditTypeIngredientComponent;
  let fixture: ComponentFixture<ModalEditTypeIngredientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalEditTypeIngredientComponent]
    });
    fixture = TestBed.createComponent(ModalEditTypeIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
