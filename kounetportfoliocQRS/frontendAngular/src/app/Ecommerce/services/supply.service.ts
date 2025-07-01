import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SupplyDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class SupplyService {
  private commandBase = `${ecpolyCommand.backend}/supply/command`;
  private queryBase = `${ecpolyQuery.backend}/api/supply`;

  constructor(private http: HttpClient) {}

  /**  Query: Liste paginée des supplies */
  getAllSupplies(page: number = 0, size: number = 10): Observable<SupplyDTO[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<SupplyDTO[]>(this.queryBase, { params });
  }

  /**  Query: Récupérer un supply par ID */
  getSupplyById(id: string): Observable<SupplyDTO> {
    return this.http.get<SupplyDTO>(`${this.queryBase}/${id}`);
  }

  /**  Command: Créer un supply avec un stock */
  createSupplyWithStock(payload: any): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create-with-stock-and-address`, payload);
  }
}
