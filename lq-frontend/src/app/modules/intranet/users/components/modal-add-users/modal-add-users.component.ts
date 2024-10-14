import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DocumentTypeId, Gender, UserMap, UserStatus, UserType } from 'src/app/core/models/users/users.interface';

@Component({
  selector: 'app-modal-add-users',
  templateUrl: './modal-add-users.component.html',
  styleUrls: ['./modal-add-users.component.css']
})
export class ModalAddUsersComponent {
  @Input() isVisible: boolean = false; 
  @Input() typeUser!: UserType[];
  @Input() gender!: Gender[];
  @Input() documentType!: DocumentTypeId[];
  @Input() userStatus!: UserStatus[];
  @Input() loadingUpdate: boolean = false;
  @Output() hideModalEmit = new EventEmitter<boolean>()
  @Output() createUserEmitter = new EventEmitter<UserMap>()
  addForm!: FormGroup;
  userOfCreate!: UserMap;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.addForm = this.fb.group({
      documentTypeId: [, [Validators.required]],
      documentNumber: [, [Validators.required]],
      firstName: [, [Validators.required]],
      secondName: [],
      firstLastName: [, [Validators.required]],
      secondLastName: [],
      userType: [, [Validators.required]],
      phone: [, [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: [, [Validators.required, Validators.email]], 
      address: [],
      gender: [, [Validators.required]],
      password: [, [Validators.required, Validators.minLength(6)]], 
      userStatus: [, [Validators.required]]
    });
  }

  hideModal() {
    this.hideModalEmit.emit(false);
  }

  createUser() {
    this.userOfCreate = {
      documentNumber: this.addForm.controls['documentNumber'].value,
      userTypeId: this.addForm.controls['userType'].value,
      genderId: this.addForm.controls['gender'].value,
      documentTypeId: this.addForm.controls['documentTypeId'].value,
      userStatusId: this.addForm.controls['userStatus'].value,
      firstName: this.addForm.controls['firstName'].value,
      secondName: this.addForm.controls['secondName'].value,
      firstLastName: this.addForm.controls['firstLastName'].value,
      secondLastName: this.addForm.controls['secondLastName'].value,
      phone: this.addForm.controls['phone'].value,
      address: this.addForm.controls['address']?.value || '',
      email: this.addForm.controls['email'].value,
      password: this.addForm.controls['password'].value,
    };
    this.createUserEmit(this.userOfCreate);
  }

  createUserEmit(user: UserMap) {
    this.createUserEmitter.emit(user);
  }
}

