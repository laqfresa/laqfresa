import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User, UserType, Gender, DocumentTypeId, UserStatus, UserMap  } from 'src/app/core/models/users/users.interface';

@Component({
  selector: 'app-modal-edit-users',
  templateUrl: './modal-edit-users.component.html',
  styleUrls: ['./modal-edit-users.component.css']
})
export class ModalEditUsersComponent {
  @Input() usersEdit!: User; 
  @Input() typeUser!: UserType[];
  @Input() gender!: Gender[];
  @Input() documentType!: DocumentTypeId[];
  @Input() userStatus!: UserStatus[];
  @Input() isVisible: boolean = false;
  @Input() loadingUpdate: boolean = false;
  @Output() hideModalEmit = new EventEmitter<boolean>()
  @Output() updateUserEmitter = new EventEmitter<UserMap>()
  editForm!: FormGroup;
  pendingValidateCant: boolean = true;
  isVisibleCant: boolean = false;
  userOfUpdate!: UserMap;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.editForm = this.fb.group({
      documentTypeId: [this.usersEdit.documentTypeId.idTypeDocument, [Validators.required]],
      documentNumber: [this.usersEdit.documentNumber, [Validators.required]],
      firstName: [this.usersEdit.firstName, [Validators.required]],
      secondName: [this.usersEdit.secondName],
      firstLastName: [this.usersEdit.firstLastName, [Validators.required]],
      secondLastName: [this.usersEdit.secondLastName],
      userType: [this.usersEdit.userType.idTypeUser, [Validators.required]],
      phone: [this.usersEdit.phone, [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: [this.usersEdit.email, [Validators.required, Validators.email]], 
      address: [this.usersEdit.address],
      gender: [this.usersEdit.gender.idGender, [Validators.required]],
      password: [this.usersEdit.password, [Validators.required, Validators.minLength(6)]], 
      userStatus: [this.usersEdit.userStatus.idStatusUser, [Validators.required]]
    });
  }
  

  submitForm(): void {
    if (this.editForm.valid) {
    }
  }

  hideModal() {
    this.hideModalEmit.emit(false);
  }

  updateUser() {
    this.userOfUpdate = {
      documentNumber: this.editForm.controls['documentNumber'].value,
      userTypeId: this.editForm.controls['userType'].value,
      genderId: this.editForm.controls['gender'].value,
      documentTypeId: this.editForm.controls['documentTypeId'].value,
      userStatusId: this.editForm.controls['userStatus'].value,
      firstName: this.editForm.controls['firstName'].value,
      secondName: this.editForm.controls['secondName'].value,
      firstLastName: this.editForm.controls['firstLastName'].value,
      secondLastName: this.editForm.controls['secondLastName'].value,
      phone: this.editForm.controls['phone'].value,
      address: this.editForm.controls['address']?.value || '',
      email: this.editForm.controls['email'].value,
      password: this.editForm.controls['password'].value,
    };
    this.updateUserEmit(this.userOfUpdate);
  }

  updateUserEmit(user: UserMap) {
    this.updateUserEmitter.emit(user);
  }
}
