import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DetailAdditional, DetailOrder, Ingredient, ProductMap } from 'src/app/core/models/order-products/products-interface';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { ProductsOrderService } from 'src/app/core/services/products-order/products-order.service';
import { TypeIngredients } from 'src/app/core/utilities/utilities-interfaces';

@Component({
  selector: 'app-tab-toppings',
  templateUrl: './tab-toppings.component.html',
  styleUrls: ['./tab-toppings.component.css']
})
export class TabToppingsComponent implements OnInit {

  @Input() product! : ProductMap;
  @Input() isCombo : boolean = false;
  @Input() comboIniciado: boolean = false;
  @Output() selectedIndex = new EventEmitter<number>();
  @Output() blockTabToppings = new EventEmitter<boolean>();
  @Output() resumeOrder = new EventEmitter<DetailOrder[]>();
  @Output() nameCustomer = new EventEmitter<string>();
  @Output() changeCombo = new EventEmitter<boolean>();
  @Output() isComboEmitter = new EventEmitter<boolean>();
  @Output() isProductEmiter = new EventEmitter<boolean>();
  @Output() resumeOrderCombo = new EventEmitter<DetailOrder[]>();
  @Output() observationEmit = new EventEmitter<string>();
  @Output() toppingsPremiumAddEmit = new EventEmitter<Ingredient[]>();
  @Output() toppingsClasicAddEmit = new EventEmitter<Ingredient[]>();
  @Output() saucesAddEmit = new EventEmitter<Ingredient[]>();
  @Output() adicionalesAddEmit = new EventEmitter<Ingredient[]>();

  loadingToppings: boolean = false;
  productForm!: FormGroup;
  typeIngredients = TypeIngredients;
  toppingsPremium!: Ingredient[];
  toppingsClasic!: Ingredient[];
  sauces!: Ingredient[];
  adicionales!: Ingredient[];

  toppingsPremiumAdd: Ingredient[] = [];
  toppingsClasicAdd: Ingredient[] = [];
  saucesAdd: Ingredient[] = [];
  adicionalesAdd: Ingredient[] = [];
  productsAdd!: DetailOrder[];
  observation: string = '';

  listOfOption: Ingredient[] = []; // Opciones filtradas para el select

  constructor(
    private productsService : ProductsOrderService,
    private fb: FormBuilder,
    private notificationService : NotificationService
  ) {}

   ngOnInit(): void {
    this.productForm = this.fb.group({
      quantity: [1, [Validators.required, Validators.min(1)]],
      customerName: ['', [Validators.required, Validators.minLength(3)]]
    });
    this.getIngredientsAndToppings();
   }

   isTouchedInput(namecontrol: string) {
      this.productForm.controls[namecontrol].markAllAsTouched();
      this.productForm.controls[namecontrol].updateValueAndValidity();
   }

   getIngredientsAndToppings() {
    this.loadingToppings = true;
    this.productsService.getActiveIngredientsAndToppingsP().subscribe(res => {
      if(res) {
        this.adicionales = res.filter(fil => fil.ingredientType.active === true);
        this.toppingsPremium = res.filter(item => item.ingredientType.ingredientTypeId === this.typeIngredients._TOPPINGS_PREMIUM_);
        this.toppingsClasic = res.filter(item => item.ingredientType.ingredientTypeId === this.typeIngredients._TOPPINGS_CLASIC_);
        this.sauces = res.filter(item => item.ingredientType.ingredientTypeId === this.typeIngredients._SAUSES_);
      }
      this.sendToppingsAndIngredientes();
      this.loadingToppings = false;
    }, error => {
      this.notificationService.error('Ocurrió un error al obtener los toppings.', 'Error')
    })
   }

   sendToppingsAndIngredientes() {
      this.toppingsPremiumAddEmit.emit(this.toppingsPremium);
      this.toppingsClasicAddEmit.emit(this.toppingsClasic);
      this.saucesAddEmit.emit(this.sauces);
      this.adicionalesAddEmit.emit(this.adicionales);
   }

   chekearToppings(aditionales: Ingredient[], toppingsPremium:Ingredient[], toppingsClasic:Ingredient[], sauces:Ingredient[]) {
    this.adicionales.forEach(item => {
      if(aditionales) {
        aditionales.forEach(add => {
          if(item.ingredientId === add.ingredientId) {
            item.checked = true;
          }
        })
      }
    })
    this.toppingsPremium.forEach(item => {
      if (toppingsPremium) {
        toppingsPremium.forEach(add => {
          if(item.ingredientId === add.ingredientId) {
            item.checked = true;
          }
        })
      }
    })
    this.toppingsClasic.forEach(item => {
      if(toppingsClasic) {
        toppingsClasic.forEach(add => {
          if(item.ingredientId === add.ingredientId) {
            item.checked = true;
          }
        })
      }
    }) 
    this.sauces.forEach(item => {
      if (sauces) {
        sauces.forEach(add => {
          if(item.ingredientId === add.ingredientId) {
            item.checked = true;
          }
        })
      }
    }) 
   }

   onToppingPremiumChange( ingredient: Ingredient ,isChecked: boolean) {
    if (isChecked) {
      this.toppingsPremiumAdd.push(ingredient);
    } else {
      const index = this.toppingsPremiumAdd.findIndex(i => i.ingredientId === ingredient.ingredientId);
      if (index !== -1) {
        this.toppingsPremiumAdd.splice(index, 1);
      }
    }
   }

   onToppingClasicChange( ingredient: Ingredient ,isChecked: boolean) {
    if (isChecked) {
      this.toppingsClasicAdd.push(ingredient);
    } else {
      const index = this.toppingsClasicAdd.findIndex(i => i.ingredientId === ingredient.ingredientId);
      if (index !== -1) {
        this.toppingsClasicAdd.splice(index, 1);
      }
    }
   }

   onSaucesChange( ingredient: Ingredient ,isChecked: boolean) {
    if (isChecked) {
      this.saucesAdd.push(ingredient);
    } else {
      const index = this.saucesAdd.findIndex(i => i.ingredientId === ingredient.ingredientId);
      if (index !== -1) {
        this.saucesAdd.splice(index, 1);
      }
    }
   }
   
  search(value: string): void {
    this.listOfOption = this.adicionales.filter(ingredient => 
      ingredient.name.toLowerCase().includes(value.toLowerCase())
    );
  }

  cancelOrSaveProduct() {
    if(!this.productsAdd) {
      this.changeCombo.emit(false);
    }
    if(this.product.isCombo) {
      this.selectedIndex.emit(1);
    }else {
      this.selectedIndex.emit(0);
    }
    this.blockTabToppings.emit(true);
    this.cleanVariablesAndForm()
  }

  cleanVariablesAndForm() {
    this.productForm.controls['customerName'].reset();
    this.toppingsPremiumAdd = [];
    this.toppingsClasicAdd = [];
    this.saucesAdd = [];
    this.adicionalesAdd = [];
    this.observation = '';
    this.noSelectToppings();
  }

  noSelectToppings() {
    this.toppingsPremium.forEach(element => {
      element.checked = false;
    });
    this.toppingsClasic.forEach(element => {
      element.checked = false;
    });
    this.sauces.forEach(element => {
      element.checked = false;
    });
  }

  addProductsOfOrder(product: ProductMap, isCombo:boolean | undefined) {
    if(isCombo) {
      this.isComboEmitter.emit(true);
      const nameCustomer = this.productForm.controls['customerName'].value;
      const productOfAdd = this.addProductAndToppings(product);
      if(productOfAdd) {
        if(this.productsAdd) {
          this.productsAdd.push(productOfAdd);
        }else {
          this.productsAdd = [];
          this.productsAdd.push(productOfAdd);
        }
      }
      this.nameCustomer.emit(nameCustomer);
      this.resumeOrderCombo.emit(this.productsAdd)
      this.cancelOrSaveProduct();
    }else {
      this.isProductEmiter.emit(true);
      const nameCustomer = this.productForm.controls['customerName'].value;
      const productOfAdd = this.addProductAndToppings(product);
      if(productOfAdd) {
        if(this.productsAdd) {
          this.productsAdd.push(productOfAdd);
        }else {
          this.productsAdd = [];
          this.productsAdd.push(productOfAdd);
        }
      }
      this.nameCustomer.emit(nameCustomer);
      this.observationEmit.emit(this.observation);
      this.resumeOrder.emit(this.productsAdd);
      this.cancelOrSaveProduct();
    }
  }

  addProductAndToppings(product: ProductMap): DetailOrder {
    let toppingsAdd: DetailAdditional[] = [];
    if(this.toppingsPremiumAdd.length > 0) {
      this.toppingsPremiumAdd.forEach(item => {
        toppingsAdd.push({
          value: item.ingredientType.value,
          ingredientId: item.ingredientId,
          isAditional : false,
          ingredientType: item.ingredientType
        });
      });
    }
    if(this.toppingsClasicAdd.length > 0) {
      this.toppingsClasicAdd.forEach(item => {
        toppingsAdd.push({
          value: item.ingredientType.value,
          ingredientId: item.ingredientId,
          isAditional : false,
          ingredientType: item.ingredientType
        });
      });   
    }
    if(this.saucesAdd.length > 0) {
      this.saucesAdd.forEach(item => {
        toppingsAdd.push({
          value: item.ingredientType.value,
          ingredientId: item.ingredientId,
          isAditional : false,
          ingredientType: item.ingredientType
        });
      });
    }
    if(this.adicionalesAdd.length > 0) {
      this.adicionalesAdd.forEach(item => {
        toppingsAdd.push({
          value: item.ingredientType.value,
          ingredientId: item.ingredientId,
          isAditional : true,
          ingredientType: item.ingredientType
        });
      });
    }
    const productAdd: DetailOrder ={
      product: {
        idProduct: product.idProduct
      },
      value: product.price,
      quantity: this.productForm.controls['quantity'].value,
      detailAdditionals:toppingsAdd,
      nameProduct: product.name,
      nameCustomer: this.productForm.controls['customerName'].value.toUpperCase().trim(),
      idCombo: product.idCombo ? product.idCombo : 0,
      observation: this.observation,
      quantitySauces: product.quantitySauces,
      quantityToppingsClasic: product.quantityToppingsClasic,
      quantityToppingsPremium: product.quantityToppingsPremium
    } 
    return productAdd
  }
}
