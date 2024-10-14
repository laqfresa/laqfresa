import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DetailAdditional, DetailOrder, Ingredient } from 'src/app/core/models/order-products/products-interface';
import { TypeIngredients } from 'src/app/core/utilities/utilities-interfaces';

@Component({
  selector: 'app-modal-edit-product',
  templateUrl: './modal-edit-product.component.html',
  styleUrls: ['./modal-edit-product.component.css']
})
export class ModalEditProductComponent implements OnInit{

  @Input() isVisebleModal: boolean = false;
  @Input() productOfEdit!: DetailOrder;
  @Input() toppingsPremiumAdd!: Ingredient[];
  @Input() toppingsClasicAdd!: Ingredient[]
  @Input() saucesAdd!: Ingredient[];
  @Input() adicionalesAdd!: Ingredient[];
  @Output() hidenModalEmitter = new EventEmitter<boolean>();
  @Output() saveChangesEmi = new EventEmitter<DetailOrder>();

  productForm!: FormGroup;
  toppingsPremiumSelected: Ingredient[] = [];
  toppingsClasicSelected: Ingredient[] = [];
  saucesSelected: Ingredient[] = [];
  adicionalesSelected: Ingredient[] = [];
  typeIngredients = TypeIngredients;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void { 
    this.distribuirToppingsDelProducto(this.productOfEdit.detailAdditionals)
    this.productForm = this.fb.group({
      product: this.fb.group({
        idProduct: [this.productOfEdit.product.idProduct, Validators.required]
      }),
      value: [this.productOfEdit.value, [Validators.required]],
      quantity: [this.productOfEdit.quantity,[ Validators.required, Validators.min(1)]], 
      nameProduct: [this.productOfEdit.nameProduct, [Validators.required, Validators.minLength(3)]],
      nameCustomer: [this.productOfEdit.nameCustomer, [Validators.required]],
      idCombo: [this.productOfEdit.idCombo],
      observation: [this.productOfEdit.observation],
      premiums: [this.toppingsPremiumSelected],
      clasic: [this.toppingsClasicSelected],
      sauces: [this.saucesSelected],
      adicionales: [this.adicionalesSelected],
    });
  }

  hideModal() {
    this.hidenModalEmitter.emit(true);
  }

  distribuirToppingsDelProducto (adicionales: DetailAdditional[]) {
    console.log(adicionales)
    adicionales.forEach(item => {
      if (item.ingredientType?.ingredientTypeId === this.typeIngredients._TOPPINGS_PREMIUM_ && !item.isAditional) {
        this.toppingsPremiumSelected.push(this.convertirTypoIngrediente(item));
      }else if (item.ingredientType?.ingredientTypeId === this.typeIngredients._TOPPINGS_CLASIC_ && !item.isAditional) {
        this.toppingsClasicSelected.push(this.convertirTypoIngrediente(item));
      }else if (item.ingredientType?.ingredientTypeId === this.typeIngredients._SAUSES_ && !item.isAditional) {
        this.saucesSelected.push(this.convertirTypoIngrediente(item));
      }else if (item.isAditional) {
        this.adicionalesSelected.push(this.convertirTypoIngrediente(item));
      }
    })
  }

  convertirTypoIngrediente(item: DetailAdditional): Ingredient {
    let ingredient: Ingredient;
    ingredient = {
      ingredientId: item.ingredientId,
      ingredientType: item.ingredientType,
      isAditional: item.isAditional,
      name: item.ingredientType.name,
      checked: true
    }
    return ingredient;
  }

  saveChanges() {
    if (this.productForm.controls['adicionales'].value) {
      let detail: DetailAdditional[] = this.productForm.controls['adicionales'].value;
      detail.forEach(item => {
        item.value === item.ingredientType.value
      })
    }
    let aditional: DetailAdditional[] = [
      ...this.productForm.controls['premiums'].value,
      ...this.productForm.controls['clasic'].value,
      ...this.productForm.controls['sauces'].value,
      ...this.productForm.controls['adicionales'].value
    ]
    console.log(aditional)
    const product: DetailOrder = {
      ...this.productOfEdit,
      nameCustomer: this.productForm.controls['nameCustomer'].value,
      quantity: this.productForm.controls['quantity'].value,
      observation: this.productForm.controls['observation'].value,
      detailAdditionals: aditional,
    }
    this.saveChangesEmi.emit(product);
  }

  compareToppings(topping1: Ingredient, topping2: Ingredient): boolean {
    return topping1 && topping2 ? topping1.ingredientId === topping2.ingredientId : topping1 === topping2;
  }

  chageAditional(event: Ingredient[]) {
      event.forEach(item => {
        item.isAditional = true;
      })
  }

}
