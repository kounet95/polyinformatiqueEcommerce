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
  private queryBase = `${ecpolyQuery.backend}/api`;

  constructor(private http: HttpClient) {}

  // Command : Créer un fournisseur
  createSupplier(supplier: SupplierDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, supplier);
  }

  // Query : Liste paginée des fournisseurs
  getAllSuppliers(page: number = 0, size: number = 10): Observable<PageResponse<SupplierDTO>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<SupplierDTO>>(
      `${this.queryBase}/suppliers`,
      { params }
    );
  }

  // Query : Détail d'un fournisseur par ID
  getSupplierById(id: string): Observable<SupplierDTO> {
    return this.http.get<SupplierDTO>(`${this.queryBase}/suppliers/${id}`);
  }

  // Command : Récupérer les évènements d'un agrégat fournisseur (event sourcing)
  getSupplierEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}