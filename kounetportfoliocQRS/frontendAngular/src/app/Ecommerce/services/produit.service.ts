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

   
  /**
   * Création d'un produit (Aggregate : ProductAggregate, Command : CreateProductCommand)
   * @param product ProductDTO
   * @param mediaFile Optionnel : fichier image du produit
   */
  createProduct(product: ProductDTO, mediaFile?: File): Observable<string> {
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }));
    if (mediaFile) {
      formData.append('media', mediaFile, mediaFile.name);
    }
    return this.http.post<string>(
      `${ecpolyCommand.backend}/product/command/create`,
      formData
    );
  }

  /**
   * Récupération paginée de tous les produits (Query)
   * @param page numéro de page (par défaut 0)
   * @param size taille de page (par défaut 10)
   */
  getAllProducts(page: number = 0, size: number = 10): Observable<PageResponse<ProductDTO>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<ProductDTO>>(
      `${ecpolyQuery.backend}/api/products`,
      { params }
    );
  }

  /**
 * Recherche avancée de produits avec filtres
 * @param page numéro de page (par défaut 0)
 * @param size taille de page (par défaut 10)
 * @param categoryId (optionnel)
 * @param couleurs (optionnel, ex: "red,green")
 * @param socialGroupId (optionnel)
 * @param productSize (optionnel)
 * @param sousCategories (optionnel, ex: "sub1,sub2")
 * @param searchKeyword (optionnel, texte libre)
 * @param selectedPriceRange (optionnel, ex: "0-50", "50-100", "100+")
 * @param sortOption (optionnel, ex: "priceAsc", "priceDesc", "featured")
 */
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
    .set('page', page.toString())
    .set('size', size.toString());

  if (categoryId)         params = params.set('categoryId', categoryId);
  if (couleurs)           params = params.set('couleurs', couleurs);
  if (socialGroupId)      params = params.set('socialGroupId', socialGroupId);
  if (productSize)        params = params.set('productSize', productSize);
  if (sousCategories)     params = params.set('sousCategories', sousCategories);
  if (searchKeyword)      params = params.set('search', searchKeyword);
  if (selectedPriceRange) params = params.set('priceRange', selectedPriceRange);
  if (sortOption)         params = params.set('sort', sortOption);

  return this.http.get<PageResponse<ProductDTO>>(
    `${ecpolyQuery.backend}/api/products`,
    { params }
  );
}


  /**
   * Récupérer un produit par son ID (Query)
   * @param productId string
   */
  getProductById(productId: string): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(
      `${ecpolyQuery.backend}/api/products/${productId}`
    );
  }

  /**
   * Upload d'une image seule, retourne l'URL
   * @param file Fichier image à uploader
   */
  uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post<string>(
      `${ecpolyCommand.backend}/product/command/upload-image`,
      formData
    );
  }

  /**
   * Récupérer les évènements d'un produit (Event sourcing)
   * @param aggregateId string
   */
  getProductEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/product/command/events/${aggregateId}`
    );
  }



}