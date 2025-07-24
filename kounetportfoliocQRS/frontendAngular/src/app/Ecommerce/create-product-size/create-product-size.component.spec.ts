import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProductSizeComponent } from './create-product-size.component';

describe('CreateProductSizeComponent', () => {
  let component: CreateProductSizeComponent;
  let fixture: ComponentFixture<CreateProductSizeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateProductSizeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateProductSizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
