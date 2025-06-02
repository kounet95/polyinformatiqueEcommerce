import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {}

  /**
   * Création d'une catégorie (Aggregate : CategoryAggregate, Command : CreateCategoryCommand)
   * @param category CategoryDTO
   */
  createCategory(category: CategoryDTO): Observable<void> {
    return this.http.post<void>(
      `${ecpolyCommand.backend}/category/create`, category
    );
  }

  /**
   * Suppression d'une catégorie (Aggregate : CategoryAggregate, Command : DeleteCategoryCommand)
   * @param categoryId string
   */
  deleteCategory(categoryId: string): Observable<void> {
    return this.http.delete<void>(
      `${ecpolyCommand.backend}/category/command/delete/${categoryId}`
    );
  }

  /**
   * Récupérer toutes les catégories (Query)
   */
  getAllCategories(): Observable<CategoryDTO[]> {
    return this.http.get<CategoryDTO[]>(
      `${ecpolyQuery.backend}/api/categories`
    );
  }

  /**
   * Récupérer une catégorie par son ID (Query)
   * @param categoryId string
   */
  getCategoryById(categoryId: string): Observable<CategoryDTO> {
    return this.http.get<CategoryDTO>(
      `${ecpolyQuery.backend}/api/categories/${categoryId}`
    );
  }
}