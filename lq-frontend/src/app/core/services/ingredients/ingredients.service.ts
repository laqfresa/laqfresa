import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs'
import { Ingredient, IngredientMap, IngredientType, IngredientTypeMap } from '../../models/ingredients/ingredients.interface';

@Injectable({
    providedIn: 'root'
})
export class IngredientService {
  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
  ){}


  getActiveIngredientsAndToppings():Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.apiUrl}/ingredientsToppingsActive`);
   }

  getAllIngredientsAndToppings():Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.apiUrl}/ingredients`);
  }

  getTypeIngredients():Observable<IngredientType[]> {
    return this.http.get<IngredientType[]>(`${this.apiUrl}/ingredientsType`);
  }

 createIngredient(ingredient : IngredientMap) {
    return this.http.post<Ingredient>(`${this.apiUrl}/ingredients/register`, ingredient);
 }

 createTypeIngredient(ingredientType : IngredientTypeMap) {
  return this.http.post<Ingredient>(`${this.apiUrl}/ingredientsType/register`, ingredientType);
 }

 updateIngredient(ingredient: Ingredient) {
  return this.http.put<Ingredient>(`${this.apiUrl}/ingredients/update`, ingredient);
 }

 updateTypeIngredient(ingredient: IngredientType) {
  return this.http.put<IngredientType>(`${this.apiUrl}/ingredientsType/update`, ingredient);
 }

 desactiveIngredient(ingredientId: number) {
  return this.http.put<Ingredient>(`${this.apiUrl}/ingredients/delete/${ingredientId}`, {});
 }
}