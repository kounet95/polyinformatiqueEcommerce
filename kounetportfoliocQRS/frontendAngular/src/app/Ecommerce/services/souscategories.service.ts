import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubcategoryDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class SouscategoriesService {

  constructor(private http: HttpClient) {}

  /**
   * Création d'une sous-catégorie (Aggregate : SubcategoryAggregate, Command : CreateSubcategoryCommand)
   * @param subcategory SubcategoryDTO
   */
  createSubcategory(subcategory: SubcategoryDTO): Observable<void> {
    return this.http.post<void>(
      `${ecpolyCommand.backend}/subcategory/command/create`, subcategory
    );
  }

  /**
   * Suppression d'une sous-catégorie (Aggregate : SubcategoryAggregate, Command : DeleteSubcategoryCommand)
   * @param subcategoryId string
   */
  deleteSubcategory(subcategoryId: string): Observable<void> {
    return this.http.delete<void>(
      `${ecpolyCommand.backend}/subcategory/command/delete/${subcategoryId}`
    );
  }

  /**
   * Récupérer toutes les sous-catégories (Query)
   */
  getAllSubcategories(): Observable<SubcategoryDTO[]> {
    return this.http.get<SubcategoryDTO[]>(
      `${ecpolyQuery.backend}/api/subcategories`
    );
  }
//Service correct pour lister
getSousCategoriesByCategoryId(categoryId: string): Observable<SubcategoryDTO[]> {
  return this.http.get<SubcategoryDTO[]>(`${ecpolyQuery.backend}/api/subcategories/by-category/${categoryId}`);
}

  /**
   * Récupérer une sous-catégorie par son ID (Query)
   * @param subcategoryId string
   */
  getSubcategoryById(subcategoryId: string): Observable<SubcategoryDTO> {
    return this.http.get<SubcategoryDTO>(
      `${ecpolyQuery.backend}/api/subcategories/${subcategoryId}`
    );
  }
}