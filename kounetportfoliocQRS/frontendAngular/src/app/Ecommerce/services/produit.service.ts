import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDTO } from '../../mesModels/models';
import { PageResponse } from '../../mesModels/page-response.model';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) {}

  /** Création avec image */
  createProduct(product: ProductDTO, mediaFile?: File): Observable<string> {
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }));
    if (mediaFile) formData.append('media', mediaFile, mediaFile.name);
    return this.http.post<string>(`${ecpolyCommand.backend}/product/command/create`, formData);
  }

  /** Récupération paginée */
  getAllProducts(page: number = 0, size: number = 10): Observable<PageResponse<ProductDTO>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResponse<ProductDTO>>(`${ecpolyQuery.backend}/api/products`, { params });
  }

  /** Recherche avancee */
  searchProducts(
    page: number = 0,
    size: number = 10,
    categoryId?: string,
    couleurs?: string,
    socialGroupId?: string,
    productSize?: string,
    sousCategories?: string,
    searchKeyword?: string,
    selectedPriceRange?: string,
    sortOption?: string
  ): Observable<PageResponse<ProductDTO>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
    if (categoryId)         params = params.set('categoryId', categoryId);
    if (couleurs)           params = params.set('couleurs', couleurs);
    if (socialGroupId)      params = params.set('socialGroupId', socialGroupId);
    if (productSize)        params = params.set('productSize', productSize);
    if (sousCategories)     params = params.set('sousCategories', sousCategories);
    if (searchKeyword)      params = params.set('search', searchKeyword);
    if (selectedPriceRange) params = params.set('priceRange', selectedPriceRange);
    if (sortOption)         params = params.set('sort', sortOption);

    return this.http.get<PageResponse<ProductDTO>>(`${ecpolyQuery.backend}/api/products`, { params });
  }

  /** Par id */
  getProductById(productId: string): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${ecpolyQuery.backend}/api/products/${productId}`);
  }

  /** Upload image */
  uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post<string>(`${ecpolyCommand.backend}/product/command/upload-image`, formData);
  }

  /** Events d'un product */
  getProductEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${ecpolyCommand.backend}/product/command/events/${aggregateId}`);
  }
}