import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private keycloak: KeycloakService) {}

  getUserId(): string | null {
    const tokenParsed = this.keycloak.getKeycloakInstance().idTokenParsed;
    return tokenParsed?.sub || null;
  }

 
}