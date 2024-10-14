import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableCombosComponent } from './table-combos.component';

describe('TableCombosComponent', () => {
  let component: TableCombosComponent;
  let fixture: ComponentFixture<TableCombosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableCombosComponent]
    });
    fixture = TestBed.createComponent(TableCombosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
