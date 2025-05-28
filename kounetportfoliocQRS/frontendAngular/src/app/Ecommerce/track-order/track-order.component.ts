import { Component } from '@angular/core';
import { TrackOrderService } from '../track-order.service';
import { OrderStatusDTO } from '../../mesModels/models'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-track-order',
  templateUrl:'./track-order.component.html',
  styleUrls: ['./track-order.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    // autres modules nécessaires
  ]
})
export class TrackOrderComponent {
  statusUpdates: OrderStatusDTO[] = [];
  error?: string;

  constructor(private orderStatusService: TrackOrderService) {}

  startTracking(orderId: string) {
    this.statusUpdates = [];
    this.error = undefined;
    this.orderStatusService.watchOrderStatus(orderId)
      .subscribe({
        next: status => this.statusUpdates.push(status),
        error: err => this.error = 'Erreur de suivi de commande'
      });
  }
}