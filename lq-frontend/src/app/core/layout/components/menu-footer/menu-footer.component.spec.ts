import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuFooter } from './menu-footer.component';

describe('SideMenu', () => {
  let component: MenuFooter;
  let fixture: ComponentFixture<MenuFooter>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MenuFooter]
    });
    fixture = TestBed.createComponent(MenuFooter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
