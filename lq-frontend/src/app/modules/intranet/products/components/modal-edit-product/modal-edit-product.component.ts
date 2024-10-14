import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Subscription } from 'rxjs';
import { LinkedDetailProduct, LinkedProduct } from 'src/app/core/models/ingredients/ingredients.interface';
import { UpdateProductBody, DetailProductMap, DetailProductMapSend, Ingredient, ProductSize } from 'src/app/core/models/order-products/products-interface';
import { TypeIngredients } from 'src/app/core/utilities/utilities-interfaces';

@Component({
  selector: 'app-modal-edit-product',
  templateUrl: './modal-edit-product.component.html',
  styleUrls: ['./modal-edit-product.component.css']
})
export class ModalEditProductComponent {
  @Input() productEdit!: LinkedProduct; 
  @Input() sizes!: ProductSize[]; 
  @Input() isVisible: boolean = false; 
  @Input() tClasics!: Ingredient[]; 
  @Input() premiums!:  Ingredient[]; 
  @Input() sauces!:  Ingredient[]; 
  @Input() capas!:  Ingredient[]; 
  @Input() loadingUpdate: boolean = false; 
  @Output() hideModalEmit = new EventEmitter<boolean>()
  @Output() updateProductEmitter = new EventEmitter<UpdateProductBody>()
  editForm!: FormGroup;
  pendingValidateCant: boolean = false;
  isVisibleCant: boolean = false;
  productOfUpdate!: UpdateProductBody;
  detailProd!: DetailProductMap[];
  typeProducts = TypeIngredients;
  toPremiums!: DetailProductMap[];
  toClasics!: DetailProductMap[];
  toSauces!: DetailProductMap[];
  toCapas!: DetailProductMap[];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.editForm = this.fb.group({
      name: [this.productEdit.name, [Validators.required]],
      description: [this.productEdit.description, [Validators.required]],
      value: [this.productEdit.value, [Validators.required, Validators.min(1000)]],
      size: [ this.productEdit.size, [Validators.required]],
      clasics:[],
      premium: [],
      sauces:[],
      capas: [],
      clasicsCantAdd:[],
      premiumCantAdd: [],
      saucesCantAdd:[],
      capasCantAdd: [],
      cantClasic: [this.productEdit.quantityClasic],
      cantPremium: [this.productEdit.quantityPremium],
      cantSauces: [this.productEdit.quantitySalsa],
    });

    this.detailProd = this.createDetailProductMap(this.productEdit);
    this.findAndSetToppings(this.detailProd);
  }

  createDetailProductMap(linkedProduct: LinkedProduct): DetailProductMap[] {
    const detailProductMap: { [key: number]: { quantity: number; name: string; idTypeIngredient: number } } = {};
  
    const addDetails = (details: LinkedDetailProduct[]) => {
      details.forEach(detail => {
        if (detail.ingredient) {
          if (detailProductMap[detail.idIngredient]) {
            detailProductMap[detail.idIngredient].quantity += detail.quantity;
          } else {
            detailProductMap[detail.idIngredient] = {
              quantity: detail.quantity,
              name: detail.ingredient.name,
              idTypeIngredient: detail.ingredient.ingredientType.ingredientTypeId,
            };
          }
        }
      });
    };
  
    addDetails(linkedProduct.salsas);
    addDetails(linkedProduct.capas);
    addDetails(linkedProduct.toppingsPremium);
    addDetails(linkedProduct.toppingsClasic);
  
    return Object.keys(detailProductMap).map(idIngredient => ({
      idIngredient: Number(idIngredient),
      quantity: detailProductMap[Number(idIngredient)].quantity,
      name: detailProductMap[Number(idIngredient)].name,
      typeId: detailProductMap[Number(idIngredient)].idTypeIngredient,
    }));
  }

  addIngredient(nameControlIngredient: string, nameControlQuantity: string) {
    let typeId: number = 0;
    let name = this.findIngredientNameById(this.editForm.controls[nameControlIngredient].value);

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
        idIngredient: this.editForm.controls[nameControlIngredient].value,
        quantity: this.editForm.controls[nameControlQuantity].value,
        name: name,
        typeId: typeId
      }
    )
    this.findAndSetToppings(this.detailProd);
    this.editForm.controls[nameControlIngredient].reset();
    this.editForm.controls[nameControlQuantity].reset();
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
      idProduct: this.productEdit.idProduct,
      name: this.editForm.controls['name'].value,
      description: this.editForm.controls['description'].value,
      value: this.editForm.controls['value'].value,
      size: this.editForm.controls['size'].value,
      quantityClasic: this.editForm.controls['cantClasic'].value || 0,
      quantityPremium: this.editForm.controls['cantPremium'].value || 0,
      quantitySalsa: this.editForm.controls['cantSauces'].value || 0,
      detailProduct: body,
      status: this.productEdit.status
    };
    this.updateProductEmit(this.productOfUpdate);
  }

  updateProductEmit(product: UpdateProductBody) {
    this.updateProductEmitter.emit(product);
  }
}
