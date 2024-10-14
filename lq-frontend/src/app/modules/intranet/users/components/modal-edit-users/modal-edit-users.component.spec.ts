import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModalEditUsersComponent } from './modal-edit-users.component';


describe('ModalEditProductComponent', () => {
  let component: ModalEditUsersComponent;
  let fixture: ComponentFixture<ModalEditUsersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalEditUsersComponent]
    });
    fixture = TestBed.createComponent(ModalEditUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
