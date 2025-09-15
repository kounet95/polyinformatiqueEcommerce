import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { LikeDTO, ProductSizeDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class LikeService implements OnInit {
  isMenuOpen = false;
  isLoggedIn = false;
  public profile: KeycloakProfile | null = null;
  private commandBase = `${ecpolyCommand.backend}/api/like`;
  private queryBase = `${ecpolyQuery.backend}/api/like`;

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {}

  async ngOnInit(): Promise<void> {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      this.keycloakService.loadUserProfile()
        .then(profile => this.profile = profile)
        .catch(() => this.profile = null);
    }
  }

  /** Command: Liker un produit (par ProductSizeId) */
  likeProduct(productSizeId: string): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${productSizeId}/like`, {});
  }

  /** Command: Retirer un like d’un produit (par ProductSizeId) */
  unlikeProduct(productSizeId: string): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${productSizeId}/unlike`, {});
  }

  /** Query: Compter les likes d’un produit (par ProductSizeId) */
  countLikesByProduct(productSizeId: string): Observable<number> {
    return this.http.get<number>(`${this.queryBase}/${productSizeId}/likes/count`);
  }

  /** Query: Obtenir la liste des likes pour un produit (par ProductSizeId) */
  getLikesByProduct(productSizeId: string): Observable<LikeDTO[]> {
    return this.http.get<LikeDTO[]>(`${this.queryBase}/${productSizeId}/likes`);
  }

  /** Query: Vérifier si un utilisateur a liké ce produit (ProductSizeId + customerId) */
  checkCustomerLiked(productSizeId: string, customerId: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.queryBase}/${productSizeId}/likes/exists?customerId=${customerId}`);
  }

  /** Query: Obtenir la liste des likes pour un client */
  getLikesByCustomer(customerId: string): Observable<LikeDTO[]> {
   return this.http.get<LikeDTO[]>(`${ecpolyQuery.backend}/api/like/customer/${customerId}/likes`);
  }

  private likeCountSubject = new BehaviorSubject<number>(0);
  likeCount$ = this.likeCountSubject.asObservable();

  private likedItemsSubject = new BehaviorSubject<any[]>([]);
  likedItems$ = this.likedItemsSubject.asObservable();

  /**
   * Rafraîchir les likes pour un produitSize (et non productId)
   */
  refreshLikes(productSizeId: string) {
    this.getLikesByProduct(productSizeId).subscribe(items => {
      this.likedItemsSubject.next(items);
      this.likeCountSubject.next(items.length);
    });
  }

 
}