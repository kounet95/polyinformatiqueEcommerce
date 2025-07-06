import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StockDTO, ProductSizeDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) {}

  /**
   * Crée un nouveau stock (Aggregate : StockAggregate, Command : AddStockCommand)
   * @param stock StockDTO
   */
  createStock(stock: StockDTO): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/stock/command/add`, stock
    );
  }

  /** Command: Création d'un stock avec son address */
  createCustomerWithAddress(payload: any): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/stock/command/create-with-address`, payload
    );
  }

  /**
   * Récupère tous les stocks (Query)
   */
  getAllStocks(): Observable<StockDTO[]> {
    return this.http.get<StockDTO[]>(
      `${ecpolyQuery.backend}/api/stocks`
    );
  }

  /**
   * Récupère les nouveaux arrivages => ProductSizeDTO[]
   */
  getNewArrivals(date: Date): Observable<ProductSizeDTO[]> {
    return this.http.get<ProductSizeDTO[]>(
      `${ecpolyQuery.backend}/api/stocks/new-arrivals`,
      {
      params: { since: date.toISOString() }
    }
  );
}

  /**
   * Récupère les produits en promotion => ProductSizeDTO[]
   */
  getOnSale(): Observable<ProductSizeDTO[]> {
    return this.http.get<ProductSizeDTO[]>(
      `${ecpolyQuery.backend}/api/stocks/on-sale`
    );
  }

  /**
   * Récupère un stock par son ID (Query)
   */
  getStockById(stockId: string): Observable<StockDTO> {
    return this.http.get<StockDTO>(
      `${ecpolyQuery.backend}/api/stocks/${stockId}`
    );
  }

  /**
   * Récupère la liste des évènements d'un agrégat Stock (Query/Event sourcing, optionnel)
   */
  getStockEvents(stockId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/stock/command/events/${stockId}`
    );
  }

}
