import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ingredient, IngredientType } from 'src/app/core/models/ingredients/ingredients.interface';

@Component({
  selector: 'app-modal-edit-ingredient',
  templateUrl: './modal-edit-ingredient.component.html',
  styleUrls: ['./modal-edit-ingredient.component.css']
})
export class ModalEditIngredientComponent implements OnInit {
  @Input() isVisible: boolean = false;
  @Input() loadingUpdate: boolean = false;
  @Input() ingredient!: Ingredient;
  @Input() typeIngredients!: IngredientType[];
  @Output() hideModalEmitter = new EventEmitter<boolean>()
  @Output() updateIngredientEmit = new EventEmitter<Ingredient>()
  editIngredient!: FormGroup;

  status = [
    {
      label: 'ACTIVO',
      value: true
    },
    {
      label: 'INACTIVO',
      value: false
    }
  ]

  constructor(
    private fb: FormBuilder
  ) {}


  ngOnInit(): void {
    this.editIngredient = this.fb.group({
      name: [this.ingredient.name, [Validators.required, Validators.minLength(3)]],
      type: [this.ingredient.ingredientType.ingredientTypeId, [Validators.required]],
      status: [this.ingredient.active, [Validators.required]]
    });
  }

  hideModal() {
    this.hideModalEmitter.emit(true);
  }

  updateIngredient(ingredient: Ingredient) {
    let ingredientBody: Ingredient = {
      ingredientId: ingredient.ingredientId,
      ingredientType: this.editIngredient.controls['type'].value,
      name: this.editIngredient.controls['name'].value,
      active: this.editIngredient.controls['status'].value
    }

    this.updateIngredientEmit.emit(ingredientBody);
  }
}
