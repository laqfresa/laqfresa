import { Component } from '@angular/core';
import { Ingredient, IngredientType } from 'src/app/core/models/ingredients/ingredients.interface';
import { IngredientService } from 'src/app/core/services/ingredients/ingredients.service';
import { NotificationService } from 'src/app/core/services/notification/notification.service';

@Component({
  selector: 'app-toppings',
  templateUrl: './toppings.component.html',
  styleUrls: ['./toppings.component.css']
})
export class ToppingsComponent {
  loadingIngredients: boolean = false;
  loadingTypesIngredients: boolean = false;
  ingredientes!: Ingredient[];
  allTypeIngredients!: IngredientType[];

  constructor(
    private ingredientsService : IngredientService,
    private notificationService : NotificationService
  ) {
    this.getAllToppings();
    this.getAllTypesIngredients();
  }

  getAllToppings() {
    this.loadingIngredients = true;
    this.ingredientsService.getAllIngredientsAndToppings().subscribe(res => {
      this.loadingIngredients = false;
      if(res) {
        this.ingredientes = res;
      }else {
        this.notificationService.error('No se encontraron toppings', 'Error')
      }
    }, error => {
      this.loadingIngredients = false;
      this.notificationService.error('Ocurrrio un error al obtener los ingredients')
    })
  }

  getAllTypesIngredients() {
    this.loadingTypesIngredients = true;
    this.ingredientsService.getTypeIngredients().subscribe(res => {
      this.loadingTypesIngredients = false;
      if(res) {
        this.allTypeIngredients = res;
      }else {
        this.notificationService.error('No se encontraron tipos de ingredientes');
      }
    }, error => {
      this.loadingTypesIngredients = false;
      this.notificationService.error('Ocurrio un error al obtener los tipos de ingredientes');
    })
  }

}
