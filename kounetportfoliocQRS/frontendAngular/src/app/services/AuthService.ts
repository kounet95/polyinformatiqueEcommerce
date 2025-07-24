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

  getUserProfile(): { firstname: string, lastname: string, email: string, phone: string } | null {
    const tokenParsed: any = this.keycloak.getKeycloakInstance().idTokenParsed;
    if (!tokenParsed) return null;
    return {
      firstname: tokenParsed.given_name || '',
      lastname: tokenParsed.family_name || '',
      email: tokenParsed.email || '',
      phone: tokenParsed.phone_number || ''
    };
  }
}