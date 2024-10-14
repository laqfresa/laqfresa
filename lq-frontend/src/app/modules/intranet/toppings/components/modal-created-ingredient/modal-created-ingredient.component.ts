import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreatedIngredient, Ingredient, IngredientType } from 'src/app/core/models/ingredients/ingredients.interface';

@Component({
  selector: 'app-modal-created-ingredient',
  templateUrl: './modal-created-ingredient.component.html',
  styleUrls: ['./modal-created-ingredient.component.css']
})
export class ModalCreatedIngredientComponent {
  @Input() isVisible: boolean = false;
  @Input() loadingUpdate: boolean = false;
  @Input() typeIngredients!: IngredientType[];
  @Output() hideModalEmitter = new EventEmitter<boolean>()
  @Output() addIngredientEmit = new EventEmitter<CreatedIngredient>()
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
      name: [ ,[Validators.required, Validators.minLength(3)]],
      type: [ ,[Validators.required]],
      status: [ true,[Validators.required]]
    });
  }

  hideModal() {
    this.hideModalEmitter.emit(true);
  }

  addIngredient() {
    let ingredientBody: CreatedIngredient = {
      ingredientType: this.editIngredient.controls['type'].value,
      name: this.editIngredient.controls['name'].value,
      active: this.editIngredient.controls['status'].value
    }

    this.addIngredientEmit.emit(ingredientBody);
  }
}
