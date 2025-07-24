import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatSupplyComponent } from './creat-supply.component';

describe('CreatSupplyComponent', () => {
  let component: CreatSupplyComponent;
  let fixture: ComponentFixture<CreatSupplyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatSupplyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatSupplyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
