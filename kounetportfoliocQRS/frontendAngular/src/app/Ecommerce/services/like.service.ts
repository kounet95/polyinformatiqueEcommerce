import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LikeDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  private commandBase = `${ecpolyCommand.backend}/api/like`;
  private queryBase = `${ecpolyQuery.backend}/api/like`;

  constructor(private http: HttpClient) {}

  /** Command: Liker un produit */
  likeProduct(productId: string): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${productId}/like`, {});
  }

  /** Command: Retirer un like d’un produit */
  unlikeProduct(productId: string): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${productId}/unlike`, {});
  }

  /** Query: Compter les likes d’un produit */
  countLikesByProduct(productId: string): Observable<number> {
    return this.http.get<number>(`${this.queryBase}/${productId}/likes/count`);
  }

  /** Query: Obtenir la liste des likes pour un produit */
  getLikesByProduct(productId: string): Observable<LikeDTO[]> {
    return this.http.get<LikeDTO[]>(`${this.queryBase}/${productId}/likes`);
  }

  /** Query: Vérifier si un utilisateur a liké ce produit */
  checkCustomerLiked(productId: string, customerId: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.queryBase}/${productId}/likes/exists?customerId=${customerId}`);
  }
}
