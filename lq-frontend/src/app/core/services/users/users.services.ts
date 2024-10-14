import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { environment } from '../../../../environments/environment.local'
import { Observable } from 'rxjs'
import { User, UserMap, DocumentTypeId, Gender, UserStatus, UserType } from '../../models/users/users.interface';

@Injectable({
    providedIn: 'root'
})
export class UserService {
  private apiUrl: string = environment.apiUrlsLQ.lq_internal;
  constructor(
    private http: HttpClient,
  ){}

 getAllUsers(): Observable<User[]> {
  return this.http.get<User[]>(`${this.apiUrl}/users`);
 }

 createUser(user : UserMap){
    return this.http.post<UserMap>(`${this.apiUrl}/register`, user);
 }

 updateUser(user : UserMap){
    return this.http.put<UserMap>(`${this.apiUrl}/updateUser`, user);
 }

 desactiveUser(userId : number){
    return this.http.put<UserMap>(`${this.apiUrl}/deleteUser/${userId}`, {});
 }

 getAllDocumentTypeId(): Observable<DocumentTypeId[]>{
   return this.http.get<DocumentTypeId[]>(`${this.apiUrl}/typeDocument`)
 }

 getAllGender(): Observable<Gender[]>{
   return this.http.get<Gender[]>(`${this.apiUrl}/genderUser`)
 }

 getAllUserStatus(): Observable<UserStatus[]>{
   return this.http.get<UserStatus[]>(`${this.apiUrl}/statusUser`)
 }

 getAllUserType(): Observable<UserType[]>{
   return this.http.get<UserType[]>(`${this.apiUrl}/role`)
 }
}