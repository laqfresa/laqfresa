import { Component } from '@angular/core';
import { User, UserType, Gender, DocumentTypeId, UserStatus } from 'src/app/core/models/users/users.interface';
import { UserService } from 'src/app/core/services/users/users.services';
import { NotificationService } from 'src/app/core/services/notification/notification.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  loadingUsers: boolean = false;
  users!: User[];
  typeUser!: UserType[];
  gender!: Gender[];
  documentType!: DocumentTypeId[];
  userStatus!: UserStatus[];

  constructor(
    private userService : UserService,
    private notificationService : NotificationService
  ) {
    this.getDocumentTypeId();
    this.getGender();
    this.getTypeUser();
    this.getUserStatus();
    this.getUsers();
  }

  getUsers() {
    this.loadingUsers = true;
    this.userService.getAllUsers().subscribe(res => {
      this.loadingUsers = false;
      if(res) {
        this.users = res;
      }
    })
  }

  getDocumentTypeId() {
    this.loadingUsers = true;
    this.userService.getAllDocumentTypeId().subscribe(res => {
      if(res) {
        this.documentType = res;
      }
    })
  }

  getTypeUser() {
    this.loadingUsers = true;
    this.userService.getAllUserType().subscribe(res => {
      if(res) {
        this.typeUser = res;
      }
    })
  }

  getGender() {
    this.loadingUsers = true;
    this.userService.getAllGender().subscribe(res => {
      if(res) {
        this.gender = res;
      }
    })
  }

  getUserStatus() {
    this.loadingUsers = true;
    this.userService.getAllUserStatus().subscribe(res => {
      if(res) {
        this.userStatus = res;
      }
    })
  }

  getFullName(user: User): string {
    return `${user.firstName} ${user.secondName ? user.secondName : ''} ${user.firstLastName} ${user.secondLastName}`.trim();
  }
}
