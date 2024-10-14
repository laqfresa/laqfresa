import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModalInfoUsersComponent } from './modal-info-users.component';

describe('ModalInfoUsersComponent', () => {
  let component: ModalInfoUsersComponent;
  let fixture: ComponentFixture<ModalInfoUsersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalInfoUsersComponent]
    });
    fixture = TestBed.createComponent(ModalInfoUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
