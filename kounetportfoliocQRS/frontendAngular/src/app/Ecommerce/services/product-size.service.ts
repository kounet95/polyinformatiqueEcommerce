import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductSizeDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';
import { Data } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductSizeService {
  constructor(private http: HttpClient) {}

  /**
   * Récupérer tous les ProductSize (Query)
   */
  getAllProductSizes(): Observable<ProductSizeDTO[]> {
    return this.http.get<ProductSizeDTO[]>(
      `${ecpolyQuery.backend}/api/productsizes`
    );
  }

  /**
   * Récupérer un ProductSize par son ID (Query)
   */
  getProductSizeById(id: string): Observable<ProductSizeDTO> {
    return this.http.get<ProductSizeDTO>(
      `${ecpolyQuery.backend}/api/productsizes/${id}`
    );
  }

   /**
   * Récupérer un Product par son ID dans ProductSize (Query)
   */
   getProductSizesByProductId(productId: string): Observable<ProductSizeDTO[]> {
  return this.http.get<ProductSizeDTO[]>(
    `${ecpolyQuery.backend}/api/productsizes?productId=${productId}`
  );
}
  /**
   * Créer un ProductSize (Command)
   * @param productSize ProductSizeDTO
   * @param mediaFile Optionnel : fichier image à uploader
   */
  createProductSize(productSize: ProductSizeDTO, mediaFile?: File): Observable<string> {
    const formData = new FormData();
    formData.append('productSize', new Blob([JSON.stringify(productSize)], { type: 'application/json' }));
    if (mediaFile) {
      formData.append('media', mediaFile, mediaFile.name);
    }
    return this.http.post<string>(
      `${ecpolyCommand.backend}/productsize/command/create`,
      formData
    );
  }

  /**
   * Supprimer un ProductSize (Command)
   */
  deleteProductSize(id: string): Observable<string> {
    return this.http.delete<string>(
      `${ecpolyCommand.backend}/productsize/command/delete/${id}`
    );
  }

  /**
   * Récupérer les événements d'un ProductSize (event sourcing, Command)
   */
  getProductSizeEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/productsize/command/events/${aggregateId}`
    );
  }


  
 getNewArrivals(date: Date): Observable<any[]> {
  const dateStr = date.toISOString();
  return this.http.get<any[]>(
    `${ecpolyCommand.backend}/api/productsizes/news/${dateStr}`
  );
}

  getSaleProducts(): Observable<any[]>{
         return this.http.get<any[]>(
      `${ecpolyCommand.backend}/api/productsizes/sale`
    );
  }
}