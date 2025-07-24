import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-order-summary-component',
  standalone: false,
  templateUrl: './order-summary-component.component.html',
  styleUrl: './order-summary-component.component.css'
})
export class OrderSummaryComponentComponent {
 @Input() subtotal?: number;
  @Input() shipping?: number;
  @Input() tax?: number;
  @Input() total?: number;
  @Input() shippingAddress?: {
    street?: string;
    suite?: string;
    city?: string;
    country?: string;
  };
  @Input() shippingMethod?: string;
}