import { Injectable, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderStatusDTO } from '../mesModels/models'; 
import { ecpolyQuery } from '../../mesApi/ecpolyQuery'; 

@Injectable({
  providedIn: 'root'
})
export class TrackOrderService {
  constructor(private http: HttpClient, private zone: NgZone) {}

  // Récupérer le statut d'une commande en mode "HTTP classique"
  public getOrderStatus(orderId: string): Observable<OrderStatusDTO> {
    return this.http.get<OrderStatusDTO>(`${ecpolyQuery.backend}/api/orderstatus/${orderId}`);
  }

  // Suivre le statut d'une commande en temps réel (SSE)
  public watchOrderStatus(orderId: string): Observable<OrderStatusDTO> {
    return new Observable<OrderStatusDTO>(observer => {
      const url = `${ecpolyQuery.backend}/api/orderstatus/watch/${orderId}`;
      const eventSource = new EventSource(url);

      eventSource.onmessage = event => {
        this.zone.run(() => {
          observer.next(JSON.parse(event.data));
        });
      };

      eventSource.onerror = error => {
        this.zone.run(() => {
          observer.error(error);
          eventSource.close();
        });
      };

      return () => eventSource.close();
    });
  }

  // Suivre le statut d'une commande par code-barres (SSE)
  public watchOrderStatusByBarcode(barcode: string): Observable<OrderStatusDTO> {
    const url = `${ecpolyQuery.backend}/api/orderstatus/watch-by-barcode/${barcode}`;
    return new Observable<OrderStatusDTO>(observer => {
      const eventSource = new EventSource(url);

      eventSource.onmessage = event => {
        this.zone.run(() => {
          observer.next(JSON.parse(event.data));
        });
      };

      eventSource.onerror = error => {
        this.zone.run(() => {
          observer.error(error);
          eventSource.close();
        });
      };

      return () => eventSource.close();
    });
  }
}