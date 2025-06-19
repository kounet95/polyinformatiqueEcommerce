import { Component } from '@angular/core';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
  standalone:false,
})
export class AccountComponent {
  user = {
    name: 'Sarah Anderson',
    bonuses: 100,
    avatar: '',
  };

  menu = [
    { icon: 'inventory_2', label: 'Orders', count: 1, selected: true },
    { icon: 'favorite_border', label: 'Wishlist' },
    { icon: 'credit_card', label: 'Payment methods' },
    { icon: 'star_border', label: 'My reviews' },
    { icon: 'person_outline', label: 'Personal info' },
    { icon: 'place', label: 'Addresses' },
    { icon: 'notifications_none', label: 'Notifications' },
  ];
  service = [
    { icon: 'help_outline', label: 'Help center' },
    { icon: 'description', label: 'Terms and conditions' },
    { icon: 'logout', label: 'Log out', color: 'warn' },
  ];

  orders = [
    {
      id: '78A6431D409',
      date: '02/15/2025',
      status: 'In progress',
      statusColor: 'accent',
      total: 2105.90,
      products: [
        { image: 'bag.png' }, { image: 'chair.png' }, { image: 'sunglasses.png' }
      ]
    },
    {
      id: '47H76G09F33',
      date: '12/10/2024',
      status: 'Delivered',
      statusColor: 'primary',
      total: 360.75,
      products: [
        { image: 'jacket.png' }
      ]
    },
    {
      id: '502TR872W2',
      date: '11/05/2024',
      status: 'Delivered',
      statusColor: 'primary',
      total: 4268.00,
      products: [
        { image: 'heels.png' }, { image: 'backpack.png' }, { image: 'jacket2.png' }, { image: '...etc' }
      ],
      details: {
        date: '11/05/2024',
        payment: 'Credit Card (**** 4589)',
        items: [
          { name: 'Quis nostrud exercitation', sku: 'PRD-005', qty: 2, price: 1299.99, image: 'heels.png' },
          { name: 'Ullamco laboris nisi', sku: 'PRD-006', qty: 1, price: 799.99, image: 'backpack.png' },
          { name: 'Aliquip ex ea commodo', sku: 'PRD-007', qty: 3, price: 449.99, image: 'jacket2.png' }
        ]
      },
      summary: {
        subtotal: 3899.94,
        shipping: 29.99,
        tax: 338.07,
        total: 4268.00,
        shippingAddress: {
          street: '456 Business Ave',
          suite: 'Suite 200',
          city: 'San Francisco, CA 94107',
          country: 'United States',
        },
        shippingMethod: 'Premium Delivery (1-2 business days)'
      }
    }
  ];

  selectedOrder = this.orders[2];
}