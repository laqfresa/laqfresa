import { Component, Input, OnInit } from '@angular/core';
import { CreatedIngredient, Ingredient, IngredientType, IngredientTypeMap } from 'src/app/core/models/ingredients/ingredients.interface';
import { IngredientService } from 'src/app/core/services/ingredients/ingredients.service';
import { NotificationService } from 'src/app/core/services/notification/notification.service';

@Component({
  selector: 'app-table-toppings',
  templateUrl: './table-toppings.component.html',
  styleUrls: ['./table-toppings.component.css']
})
export class TableToppingsComponent implements OnInit{

  @Input() ingredients!: Ingredient[];
  @Input() loadingIngredients: boolean = false;
  @Input() loadingTypesIngredients: boolean = false;
  @Input() allTypesIngredients!: IngredientType[];
  isVisibleModalEdit: boolean = false;
  ingredientModal!: Ingredient;
  typesIngredients!: IngredientType[];
  loadingUpdate: boolean = false;
  isVisibleModalEditType: boolean = false;
  typeEdited!: IngredientType;
  isVisbleModalAddIngredient: boolean = false;
  isVisibleModalAddType: boolean = false;

  constructor(
    private ingredientsService : IngredientService,
    private notificationService : NotificationService
  ) {}

  ngOnInit(): void {
    this.getTypeIngredients();
  }

  showModalEdit(ingredient: Ingredient) {
    this.isVisibleModalEdit = true;
    this.ingredientModal = ingredient;
  }

  getTypeIngredients() {
    this.ingredientsService.getTypeIngredients().subscribe(res => {
      if(res) {
        let types = res.filter(fil => fil.active === true);
        this.typesIngredients = types;
      }else {
        this.notificationService.error('No se encontraron tipos de ingredientes', 'Error')
      }
    }, error => {
      this.notificationService.error('Ocurrio un error al obtener los ingredientes', 'Error')
    })
  }

  hideModalEdit() {
    this.isVisibleModalEdit = false;
  }

  updateIngredient(ingredient: Ingredient) {
    this.loadingUpdate = true;
    this.ingredientsService.updateIngredient(ingredient).subscribe(res => {
      this.loadingUpdate = false;
      this.notificationService.success('Se ha actualizado el ingrediente correctamente', 'Exito')
      window.location.reload()
    }, error => {
      this.loadingUpdate = false;
      this.notificationService.error('Ocurrio un error al actualizar el producto', 'Error');
    })
  }

  showModalEditType(typeIngredient : IngredientType) {
    this.isVisibleModalEditType = true;
    this.typeEdited = typeIngredient;
  }

  hideModalEditType() {
    this.isVisibleModalEditType = false;
  }

  updateIngredientType(typeIngredients: IngredientType) {
    this.loadingUpdate = true;
    this.ingredientsService.updateTypeIngredient(typeIngredients).subscribe(res => {
      this.loadingUpdate = false;
      this.notificationService.success('Se ha actualizado el tipo de ingrediente correctamente', 'Exito')
      window.location.reload();
    }, error => {
      this.loadingUpdate = false;
      this.notificationService.error('Ocurrio un error al actualizar el tipo de ingrediente', 'Error');
    })
  }

  showModalAddIngredient() {
    this.isVisbleModalAddIngredient = true;
  }

  hideModalAddIngredient() {
    this.isVisbleModalAddIngredient = false;
  }

  addIngredient(body:CreatedIngredient ) {
    this.loadingUpdate = true;
    this.ingredientsService.createIngredient(body).subscribe(res => {
      this.loadingUpdate = false;
      this.notificationService.success('Se ha creado el ingrediente correctamente');
      this.hideModalAddIngredient();
      window.location.reload();
    }, error => {
      this.notificationService.error('Ocurrio un error al crear el ingrediente intente mas tarde')
    })
  }

  showModalAddType() {
    this.isVisibleModalAddType = true;
  }

  
  hideModalAddType() {
    this.isVisibleModalAddType = false;
  }
  
  addTypeIngredient(body:IngredientTypeMap ) {
    this.loadingUpdate = true;
    this.ingredientsService.createTypeIngredient(body).subscribe(res => {
      this.loadingUpdate = false;
      this.notificationService.success('Se ha creado el tipo de ingrediente correctamente');
      this.hideModalAddIngredient();
      window.location.reload();
    }, error => {
      this.notificationService.error('Ocurrio un error al crear el tipo de ingrediente intente mas tarde');
    })
  }
}
