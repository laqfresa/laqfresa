import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalResumeOrderComponent } from './modal-resume-order.component';

describe('ModalResumeOrderComponent', () => {
  let component: ModalResumeOrderComponent;
  let fixture: ComponentFixture<ModalResumeOrderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalResumeOrderComponent]
    });
    fixture = TestBed.createComponent(ModalResumeOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
