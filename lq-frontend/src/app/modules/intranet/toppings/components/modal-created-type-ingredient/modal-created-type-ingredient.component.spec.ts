import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCreatedTypeIngredientComponent } from './modal-created-type-ingredient.component';

describe('ModalCreatedTypeIngredientComponent', () => {
  let component: ModalCreatedTypeIngredientComponent;
  let fixture: ComponentFixture<ModalCreatedTypeIngredientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalCreatedTypeIngredientComponent]
    });
    fixture = TestBed.createComponent(ModalCreatedTypeIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
