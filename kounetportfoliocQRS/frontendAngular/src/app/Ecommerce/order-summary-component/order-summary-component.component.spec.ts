import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderSummaryComponentComponent } from './order-summary-component.component';

describe('OrderSummaryComponentComponent', () => {
  let component: OrderSummaryComponentComponent;
  let fixture: ComponentFixture<OrderSummaryComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrderSummaryComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderSummaryComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
