import { Component } from '@angular/core';
import { TrackOrderService } from '../track-order.service';
import { OrderStatusDTO } from '../../mesModels/models'; 

@Component({
  selector: 'app-track-order',
  templateUrl: './track-order.component.html',
  styleUrls: ['./track-order.component.css']
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