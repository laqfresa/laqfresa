import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEditIngredientComponent } from './modal-edit-ingredient.component';

describe('ModalEditIngredientComponent', () => {
  let component: ModalEditIngredientComponent;
  let fixture: ComponentFixture<ModalEditIngredientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalEditIngredientComponent]
    });
    fixture = TestBed.createComponent(ModalEditIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
