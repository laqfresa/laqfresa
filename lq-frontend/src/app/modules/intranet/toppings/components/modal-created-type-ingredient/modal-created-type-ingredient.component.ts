import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreatedIngredient, IngredientTypeMap } from 'src/app/core/models/ingredients/ingredients.interface';
import { IngredientType } from 'src/app/core/models/order-products/products-interface';

@Component({
  selector: 'app-modal-created-type-ingredient',
  templateUrl: './modal-created-type-ingredient.component.html',
  styleUrls: ['./modal-created-type-ingredient.component.css']
})
export class ModalCreatedTypeIngredientComponent {
  @Input() isVisible: boolean = false;
  @Input() loadingUpdate: boolean = false;
  @Output() hideModalEmitter = new EventEmitter<boolean>()
  @Output() addTypeIngredientEmit = new EventEmitter<IngredientTypeMap>()
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
      value: [ 100, [Validators.required, Validators.min(100)]],
      status: [ true,[Validators.required]]
    });
  }

  hideModal() {
    this.hideModalEmitter.emit(true);
  }

  addTypeIngredient() {
    let ingredientBody: IngredientTypeMap = {
      name: this.editIngredient.controls['name'].value,
      value: this.editIngredient.controls['value'].value,
      active: this.editIngredient.controls['status'].value
    }

    this.addTypeIngredientEmit.emit(ingredientBody);
  }
}
