import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCreatedIngredientComponent } from './modal-created-ingredient.component';

describe('ModalCreatedIngredientComponent', () => {
  let component: ModalCreatedIngredientComponent;
  let fixture: ComponentFixture<ModalCreatedIngredientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalCreatedIngredientComponent]
    });
    fixture = TestBed.createComponent(ModalCreatedIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
