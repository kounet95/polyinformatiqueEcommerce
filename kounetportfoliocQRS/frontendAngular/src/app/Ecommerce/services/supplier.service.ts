import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SupplierDTO } from '../../mesModels/models';
import { PageResponse } from '../../mesModels/page-response.model';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private commandBase = `${ecpolyCommand.backend}/supplier/command`;
  private queryBase = `${ecpolyQuery.backend}/api/suppliers`;

  constructor(private http: HttpClient) {}

  /** Command: Créer un supplier */
  createSupplier(supplier: SupplierDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, supplier);
  }

  /**  Command: Créer un supplier avec address */
  createSupplierWithAddress(payload: any): Observable<string> {
    return this.http.post<string>(
      `${this.commandBase}/create-with-address`,
      payload
    );
  }

  /**  Query: Liste paginée des suppliers */
  getAllSuppliers(
    page: number = 0,
    size: number = 10
  ): Observable<PageResponse<SupplierDTO[]> >{
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<SupplierDTO[]>>
    (`${this.queryBase}`, { params });
  }

  /**  Query: Détail supplier par ID */
  getSupplierById(id: string): Observable<SupplierDTO> {
    return this.http.get<SupplierDTO>(`${this.queryBase}/${id}`);
  }

  /** Command: Lire le flux d’events pour un agrégat supplier */
  getSupplierEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}
