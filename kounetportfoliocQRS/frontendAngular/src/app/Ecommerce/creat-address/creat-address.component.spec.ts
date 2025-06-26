import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatAddressComponent } from './creat-address.component';

describe('CreatAddressComponent', () => {
  let component: CreatAddressComponent;
  let fixture: ComponentFixture<CreatAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatAddressComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
