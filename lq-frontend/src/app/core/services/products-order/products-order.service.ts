import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs'
import { Ingredient, Order, Product } from '../../models/order-products/products-interface';
import { Combo } from '../../models/combos/combos.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductsOrderService {

  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
  ){}

 getAllProducts(): Observable<Product[]> {
  return this.http.get<Product[]>(`${this.apiUrl}/products`);
 }

 getActiveIngredientsAndToppingsP():Observable<Ingredient[]> {
  return this.http.get<Ingredient[]>(`${this.apiUrl}/ingredientsToppingsActive`);
 }

 createOrder(order: Order) {
  return this.http.post<number>(`${this.apiUrl}/order`, order);
 }

 getCombosActive(): Observable<Combo[]> {
  return this.http.get<Combo[]>(`${this.apiUrl}/combosActive`);
 }

 changeStatus(idProduct: number) {
  return this.http.put<number>(`${this.apiUrl}/product/delete/${idProduct}`, {});
 }

 getTicketOrder(ticketId: number): Observable<Blob> {
  const params = new HttpParams().set('ticket', ticketId.toString());
  return this.http.get(`${this.apiUrl}/pdf/ticketOrder`, { params, responseType: 'blob' });
}
}