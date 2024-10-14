import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs'
import { CreatedProductBody, Ingredient, Product, ProductCreateMap, ProductSize, ProductUpdateMap } from '../../models/order-products/products-interface';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
  ){}

 getAllProducts(): Observable<Product[]> {
  return this.http.get<Product[]>(`${this.apiUrl}/products`);
 }

 getProduct(numberProduct : number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/${numberProduct}`);
 }

 getSizes(): Observable<ProductSize[]> {
    return this.http.get<ProductSize[]>(`${this.apiUrl}/sizes`);
 }

 getActiveIngredientsAndToppings():Observable<Ingredient[]> {
   return this.http.get<Ingredient[]>(`${this.apiUrl}/ingredientsToppingsActive`);
  }

 createProduct(product : CreatedProductBody) {
    return this.http.post<CreatedProductBody>(`${this.apiUrl}/product`, product);
 }

 updateProduct(product : ProductUpdateMap) {
    return this.http.put<ProductUpdateMap>(`${this.apiUrl}/product`, product);
 }
}