import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from 'src/app/core/models/users/users.interface';

@Component({
  selector: 'app-modal-info-users',
  templateUrl: './modal-info-users.component.html',
  styleUrls: ['./modal-info-users.component.css']
})
export class ModalInfoUsersComponent {
  @Input() users!: User;
  @Input() isVisible: boolean = false;
  @Output() hideModalEmitter = new EventEmitter<boolean>()

  hideModalEmit() {
    this.hideModalEmitter.emit(false);
  }

  getFullName(user: User): string {
    return `${user.firstName} ${user.secondName ? user.secondName : ''} ${user.firstLastName} ${user.secondLastName}`.trim();
  }
}
