export interface UserType {
  idTypeUser: number;
  name: string;
}

export interface Gender {
  idGender: number;
  name: string;
}

export interface DocumentType {
  idTypeDocument: number;
  name: string;
}

export interface UserStatus {
  idStatusUser: number;
  name: string;
}

export interface User {
  documentNumber: number;
  userType: UserType;
  gender: Gender;
  documentTypeId: DocumentType;
  userStatus: UserStatus;
  firstName: string;
  secondName: string;
  firstLastName: string;
  secondLastName: string;
  phone: string;
  address: string;
  email: string;
  password: string;
}

export enum RolesLQFresa {
  _ADMIN_ = "Administrador",
  _WAITER_ = "Mesero"
}