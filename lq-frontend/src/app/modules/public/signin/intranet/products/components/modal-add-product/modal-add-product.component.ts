import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LinkedProduct, LinkedDetailProduct } from 'src/app/core/models/ingredients/ingredients.interface';
import { ProductSize, Ingredient, UpdateProductBody, DetailProductMap, CreatedProductBody } from 'src/app/core/models/order-products/products-interface';
import { TypeIngredients } from 'src/app/core/utilities/utilities-interfaces';

@Component({
  selector: 'app-modal-add-product',
  templateUrl: './modal-add-product.component.html',
  styleUrls: ['./modal-add-product.component.css']
})
export class ModalAddProductComponent {
  @Input() sizes!: ProductSize[]; 
  @Input() isVisible: boolean = false; 
  @Input() tClasics!: Ingredient[]; 
  @Input() premiums!:  Ingredient[]; 
  @Input() sauces!:  Ingredient[]; 
  @Input() capas!:  Ingredient[]; 
  @Input() loadingUpdate: boolean = false; 
  @Output() hideModalEmit = new EventEmitter<boolean>()
  @Output() updateProductEmitter = new EventEmitter<CreatedProductBody>()
  addForm!: FormGroup;
  productOfUpdate!: CreatedProductBody;
  detailProd: DetailProductMap[] = [];
  typeProducts = TypeIngredients;
  toPremiums: DetailProductMap[] = [];
  toClasics: DetailProductMap[] = [];
  toSauces: DetailProductMap[] = [];
  toCapas: DetailProductMap[] = [];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.addForm = this.fb.group({
      name: [, [Validators.required, Validators.minLength(3)]],
      description: [ ,[Validators.required, Validators.minLength(3)]],
      value: [, [Validators.required, Validators.min(1000)]],
      size: [, [Validators.required]],
      clasics:[],
      premium: [],
      sauces:[],
      capas: [],
      clasicsCantAdd:[],
      premiumCantAdd: [],
      saucesCantAdd:[],
      capasCantAdd: [],
      cantClasic: [0],
      cantPremium: [0],
      cantSauces: [0],
    });
  }

  addIngredient(nameControlIngredient: string, nameControlQuantity: string) {
    let typeId: number = 0;
    let name = this.findIngredientNameById(this.addForm.controls[nameControlIngredient].value);

    switch (nameControlIngredient) {
      case 'premium':
        typeId = this.typeProducts._TOPPINGS_PREMIUM_;
        break;
      case 'clasics':
        typeId = this.typeProducts._TOPPINGS_CLASIC_;
        break;
      case 'sauces':
        typeId = this.typeProducts._SAUSES_;
        break;
      case 'capas':
        typeId = this.typeProducts._CAPAS_;
        break;
    }
    this.detailProd.push(
      {
        idIngredient: this.addForm.controls[nameControlIngredient].value,
        quantity: this.addForm.controls[nameControlQuantity].value,
        name: name,
        typeId: typeId
      }
    )
    this.findAndSetToppings(this.detailProd);
    this.addForm.controls[nameControlIngredient].reset();
    this.addForm.controls[nameControlQuantity].reset();
  }

  findAndSetToppings(detail: DetailProductMap[]) {
    this.toCapas = detail.filter(fil => fil.typeId === this.typeProducts._CAPAS_);
    this.toClasics = detail.filter(fil => fil.typeId === this.typeProducts._TOPPINGS_CLASIC_);
    this.toPremiums = detail.filter(fil => fil.typeId === this.typeProducts._TOPPINGS_PREMIUM_);
    this.toSauces = detail.filter(fil => fil.typeId === this.typeProducts._SAUSES_);
  }

  findIngredientNameById(id: number[]): string {
    let nameIngredient: string;
    const allIngredients = [...this.tClasics, ...this.premiums, ...this.sauces, ...this.capas];
    const foundIngredient = allIngredients.find(ingredient => id.includes(ingredient.ingredientId));

    if (foundIngredient) {
      nameIngredient = foundIngredient.name;
    } else {
      nameIngredient = '';
    }
    return nameIngredient
  }

  removeIngredient(idIngredient: number) {
    this.detailProd = this.detailProd.filter(detail => detail.idIngredient !== idIngredient);
    this.findAndSetToppings(this.detailProd);
  }

  hideModal() {
    this.hideModalEmit.emit(false);
  }

  updateProduct() {

    const body = this.detailProd.map(item => ({
      idIngredient: Array.isArray(item.idIngredient) ? item.idIngredient[0] : item.idIngredient,
      quantity: item.quantity
    }));
    
    this.productOfUpdate = {
      name: this.addForm.controls['name'].value,
      description: this.addForm.controls['description'].value,
      value: this.addForm.controls['value'].value,
      size: this.addForm.controls['size'].value,
      quantityClasic: this.addForm.controls['cantClasic'].value || 0,
      quantityPremium: this.addForm.controls['cantPremium'].value || 0,
      quantitySalsa: this.addForm.controls['cantSauces'].value || 0,
      detailProduct: body,
      status: 'ACTIVO'
    };
    this.updateProductEmit(this.productOfUpdate);
  }

  updateProductEmit(product: CreatedProductBody) {
    this.updateProductEmitter.emit(product);
  }
}

