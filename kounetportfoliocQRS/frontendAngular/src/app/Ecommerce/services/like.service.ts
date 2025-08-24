import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { LikeDTO } from '../../mesModels/models';
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

  private likeCountSubject = new BehaviorSubject<number>(0);
  likeCount$ = this.likeCountSubject.asObservable();

  private likedItemsSubject = new BehaviorSubject<any[]>([]);
  likedItems$ = this.likedItemsSubject.asObservable();

  /**
   * Rafraîchir les likes pour un produit (pour l'utilisateur connecté)
   * Tu dois passer le productId ici, car le backend ne supporte pas la liste des likes par client !
   */
  refreshLikes(productId: string) {
    this.getLikesByProduct(productId).subscribe(items => {
      this.likedItemsSubject.next(items);
      this.likeCountSubject.next(items.length);
    });
  }
}