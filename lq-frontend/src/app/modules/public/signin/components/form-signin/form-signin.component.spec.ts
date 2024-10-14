import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSigninComponent } from './form-signin.component';

describe('FormSigninComponent', () => {
  let component: FormSigninComponent;
  let fixture: ComponentFixture<FormSigninComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormSigninComponent]
    });
    fixture = TestBed.createComponent(FormSigninComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
