export interface UserType {
  idTypeUser: number;
  name: string;
}

export interface Gender {
  idGender: number;
  name: string;
}

export interface DocumentTypeId {
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
  documentTypeId: DocumentTypeId;
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

export interface UserMap {
  documentNumber: number;
  userTypeId: string;
  genderId: string;
  documentTypeId: string;
  userStatusId: string;
  firstName: string;
  secondName: string;
  firstLastName: string;
  secondLastName: string;
  phone: string;
  address: string;
  email: string;
  password: string;
}
