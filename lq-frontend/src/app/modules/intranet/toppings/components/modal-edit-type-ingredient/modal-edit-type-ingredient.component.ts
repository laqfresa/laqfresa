import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IngredientType } from 'src/app/core/models/order-products/products-interface';

@Component({
  selector: 'app-modal-edit-type-ingredient',
  templateUrl: './modal-edit-type-ingredient.component.html',
  styleUrls: ['./modal-edit-type-ingredient.component.css']
})
export class ModalEditTypeIngredientComponent implements OnInit {
  @Input() isVisible: boolean = false;
  @Input() loadingUpdate: boolean = false;
  @Input() ingredient!: IngredientType;
  @Output() hideModalEmitter = new EventEmitter<boolean>()
  @Output() updateIngredientEmit = new EventEmitter<IngredientType>()
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
      status: [this.ingredient.active, [Validators.required]],
      value: [this.ingredient.value, [Validators.required]]
    });
  }

  hideModal() {
    this.hideModalEmitter.emit(true);
  }

  updateIngredient(ingredient: IngredientType) {
    let ingredientBody: IngredientType = {
      ingredientTypeId: ingredient.ingredientTypeId,
      name: this.editIngredient.controls['name'].value,
      active: this.editIngredient.controls['status'].value,
      value: this.editIngredient.controls['value'].value
    }

    this.updateIngredientEmit.emit(ingredientBody);
  }
}
