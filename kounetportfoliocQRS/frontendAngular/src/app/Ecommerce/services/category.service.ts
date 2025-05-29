import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryDTO } from '../../mesModels/models';
import { commandbolg } from '../../../mesApi/commandeBlog';

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
      `${commandbolg.backend}/category/create`, category
    );
  }

  /**
   * Suppression d'une catégorie (Aggregate : CategoryAggregate, Command : DeleteCategoryCommand)
   * @param categoryId string
   */
  deleteCategory(categoryId: string): Observable<void> {
    return this.http.delete<void>(
      `${commandbolg.backend}/category/command/delete/${categoryId}`
    );
  }
}