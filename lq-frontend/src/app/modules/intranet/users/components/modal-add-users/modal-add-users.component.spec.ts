import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModalAddUsersComponent } from './modal-add-users.component';

describe('ModalAddUsersComponent', () => {
  let component: ModalAddUsersComponent;
  let fixture: ComponentFixture<ModalAddUsersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalAddUsersComponent]
    });
    fixture = TestBed.createComponent(ModalAddUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
