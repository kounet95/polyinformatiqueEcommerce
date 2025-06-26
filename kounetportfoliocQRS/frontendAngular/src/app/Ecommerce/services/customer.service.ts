import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerEcommerceDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private commandBase = `${ecpolyCommand.backend}/customer/command`;
  private queryBase = `${ecpolyQuery.backend}/api/customers`;

  constructor(private http: HttpClient) {}

  /** Command: Création d'un client */
  createCustomer(customer: CustomerEcommerceDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, customer);
  }

  /** Command: Suppression d'un client */
  deleteCustomer(customerId: string): Observable<string> {
    return this.http.delete<string>(`${this.commandBase}/delete/${customerId}`);
  }

  /** Query: Liste de tous les clients */
  getAllCustomers(): Observable<CustomerEcommerceDTO[]> {
    return this.http.get<CustomerEcommerceDTO[]>(`${this.queryBase}`);
  }

  /** Query: Détail d'un client par ID */
  getCustomerById(id: string): Observable<CustomerEcommerceDTO> {
    return this.http.get<CustomerEcommerceDTO>(`${this.queryBase}/${id}`);
  }

  /** Command: Obtenir les événements d'un client (Event Sourcing) */
  getCustomerEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}