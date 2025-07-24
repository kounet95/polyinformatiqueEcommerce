import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddressDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private commandBase = `${ecpolyCommand.backend}/address`;
  private queryBase = `${ecpolyQuery.backend}/api/address`;

  constructor(private http: HttpClient) {}

  /** Command: Création d'une adresse */
  createAddress(address: AddressDTO): Observable<AddressDTO> {
    return this.http.post<AddressDTO>(`${this.commandBase}/create`, address);
  }
  /** Command: Suppression d'une adresse */
  deleteAddress(addressId: string): Observable<string> {
    return this.http.delete<string>(`${this.commandBase}/delete/${addressId}`);
  }

  /** Query: Liste de toutes les adresses */
  getAllAddresses(): Observable<AddressDTO[]> {
    return this.http.get<AddressDTO[]>(`${this.queryBase}`);
  }

  /** Query: Liste des adresses d'un client */
getAddressesByCustomerId(customerId: string): Observable<AddressDTO[]> {
  return this.http.get<AddressDTO[]>(`${this.queryBase}/customer/${customerId}`);
}

  /** Query: Détail d'une adresse par ID */
  getAddressById(id: string): Observable<AddressDTO> {
    return this.http.get<AddressDTO>(`${this.queryBase}/${id}`);
  }

  /** Command: Obtenir les événements d'une adresse (Event Sourcing) */
  getAddressEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}