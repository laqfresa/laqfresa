import { CompilerOptions, Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs'
import { Combo, ComboMap, ComboUpdateMap } from '../../models/combos/combos.interface';

@Injectable({
  providedIn: 'root'
})
export class CombosService {

  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
  ){}
 getAllCombos(): Observable<Combo[]> {
  return this.http.get<Combo[]>(`${this.apiUrl}/combo`);
 }

 getCombosActive(): Observable<Combo[]> {
    return this.http.get<Combo[]>(`${this.apiUrl}/combo`);
 }

 createCombo(combo : ComboMap) {
    return this.http.post<ComboMap>(`${this.apiUrl}/combo/register`, combo);
 }

 updateCombo(combo : ComboUpdateMap) {
    return this.http.put<ComboUpdateMap>(`${this.apiUrl}/combo/register`, combo);
 }

 chageStatusCombo(comboId: number) {
    return this.http.put<Combo>(`${this.apiUrl}/ingredients/delete/${comboId}`, {});
   }
}