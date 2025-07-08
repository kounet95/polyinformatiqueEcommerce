import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page, ProductSizeDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';
import { PageResponse } from '../../mesModels/page-response.model';

@Injectable({
  providedIn: 'root'
})
export class ProductSizeService {
  constructor(private http: HttpClient) {}

  /** Liste paginée avec filtres dynamiques */
  /** Liste paginée */
  getAllProductSizes(
    page: number = 0,
    size: number = 10,
    productSize?: string,
    selectedPrice: number = 0,
    selectedPricePromo: number = 0,
    sortOption?: string
  ): Observable<Page<ProductSizeDTO>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('selectedPrice', selectedPrice)
      .set('selectedPricePromo', selectedPricePromo);

    if (productSize) params = params.set('productSize', productSize);
    if (sortOption) params = params.set('sortOption', sortOption);

    return this.http.get<Page<ProductSizeDTO>>(`${ecpolyQuery.backend}/api/productsizes`, { params });
  }

   /** Récupération paginée */
    getAllProductsise(page: number = 0, size: number = 10):
     Observable<PageResponse<ProductSizeDTO>> {
      const params = new HttpParams().set('page', page).set('size', size);

      return this.http.get<PageResponse<ProductSizeDTO>>
      (`${ecpolyQuery.backend}/api/productsizes/all`, { params });
    }

  /** Recherche avancée sur ProductSize */
 searchProductSizes(
  productName?: string,
  minPromo?: number,
  maxPromo?: number,
  size?: string,
  sale?: boolean,
  newSince?: string,
  subcategoryId?: string,
  socialGroupId?: string
): Observable<ProductSizeDTO[]> {
  let params = new HttpParams();
  if (productName) params = params.set('productName', productName);
  if (minPromo !== undefined) params = params.set('minPromo', minPromo.toString());
  if (maxPromo !== undefined) params = params.set('maxPromo', maxPromo.toString());
  if (size) params = params.set('size', size);
  if (sale !== undefined) params = params.set('sale', sale.toString());
  if (newSince) params = params.set('newSince', newSince);
  if (subcategoryId) params = params.set('subcategoryId', subcategoryId);
  if (socialGroupId) params = params.set('socialGroupId', socialGroupId);
 
  return this.http.get<ProductSizeDTO[]>(`${ecpolyQuery.backend}/api/productsizes/search`, { params });
}
 /** Un ou plusieurs ProductSize liés à un ID */
/** Un ProductSize par son id */
getProductSizeById(id: string): Observable<ProductSizeDTO> {
  return this.http.get<ProductSizeDTO>(`${ecpolyQuery.backend}/api/productsizes/${id}`);
}



 getProductSizesByProductId(productId: string): Observable<ProductSizeDTO[]> {
  return this.http.get<ProductSizeDTO[]>(`${ecpolyQuery.backend}/api/productsizes/${productId}`);
}

  /** Création */
 createProductSize(productSize: ProductSizeDTO, imageFiles: { [key: string]: File | null }): Observable<string> {
  const formData = new FormData();
  formData.append('productSize', new Blob([JSON.stringify(productSize)], { type: 'application/json' }));

  for (const key of Object.keys(imageFiles)) {
    const file = imageFiles[key];
    if (file) {
      formData.append('media', file, file.name);
    }
  }

  return this.http.post<string>(`${ecpolyCommand.backend}/productsize/command/create`, formData);
}


  /** Suppression */
  deleteProductSize(id: string): Observable<string> {
    return this.http.delete<string>(`${ecpolyCommand.backend}/productsize/command/delete/${id}`);
  }

  /** Event sourcing */
  getProductSizeEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${ecpolyCommand.backend}/productsize/command/events/${aggregateId}`);
  }

  /** Nouveautés */
  getNewArrivals(date: Date): Observable<ProductSizeDTO[]> {
    const dateStr = date.toISOString();
    return this.http.get<ProductSizeDTO[]>(`${ecpolyQuery.backend}/api/productsizes/news?date=${dateStr}`);
  }

  /** En solde */
  getSaleProducts(): Observable<ProductSizeDTO[]> {
    return this.http.get<ProductSizeDTO[]>(`${ecpolyQuery.backend}/api/productsizes/sale`);
  }
}