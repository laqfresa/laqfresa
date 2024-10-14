import { Component, Input } from '@angular/core';
import { User, UserType, Gender, DocumentTypeId, UserStatus, UserMap } from 'src/app/core/models/users/users.interface';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { UserService } from 'src/app/core/services/users/users.services';

@Component({
  selector: 'app-table-users',
  templateUrl: './table-users.component.html',
  styleUrls: ['./table-users.component.css']
})
export class TableUsersComponent {
  @Input() users!: User[];
  @Input() typeUser!: UserType[];
  @Input() gender!: Gender[];
  @Input() documentType!: DocumentTypeId[];
  @Input() userStatus!: UserStatus[];
  @Input() loadingUsers: boolean = false;

  isvisibleModal: boolean = false;
  usersModal!: User;
  isVisibleModalEdit: boolean = false;
  usersEdit!: User;
  loadingUpdate: boolean = false;
  isVisibleModalAdd: boolean = false;

  constructor(
    private userService : UserService,
    private notificationService : NotificationService
  ) {
    
  }
  
  getFullName(user: User): string {
    return `${user.firstName} ${user.secondName ? user.secondName : ''} ${user.firstLastName} ${user.secondLastName}`.trim();
  }

  showModal(users: User) {
    this.usersModal = users;
    this.isvisibleModal = true;
  }

  hideModalInfo(hide: boolean) {
    this.isvisibleModal = hide;
  }

  showModalEdit(users: User) {
    this.usersEdit = users;
    this.isVisibleModalEdit = true;
  }

  hideModalEdit() {
    this.isVisibleModalEdit = false;
  }

  createUser(user: UserMap) {
    this.loadingUpdate = true;
    this.userService.createUser(user).subscribe(res => {
      this.notificationService.success('Usuario creado correctamente', 'Exito')
      this.loadingUpdate = false;
      setTimeout(()=> {
        window.location.reload()
      },500)
    }, error => {
      this.loadingUpdate = false;
      this.notificationService.error('Ocurrio un error al crear el usuario, intente mas tarde', 'Error')
    })
  }

  showModalCreated() {
    this.isVisibleModalAdd = true;
  }

  hideModalCreated() {
    this.isVisibleModalAdd = false;
  }

  updateUser(user: UserMap) {
    this.loadingUpdate = true;
    this.userService.updateUser(user).subscribe(res => {
      this.notificationService.success('Usuario actualizado correctamente', 'Exito')
      this.loadingUpdate = false;
      setTimeout(()=> {
        window.location.reload()
      },500)
    }, error => {
      this.loadingUpdate = false;
      this.notificationService.error('Ocurrio un error al crear el usuario, intente mas tarde', 'Error')
    })
  }
}
