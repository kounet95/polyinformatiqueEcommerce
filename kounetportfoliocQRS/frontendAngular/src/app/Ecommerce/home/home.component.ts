import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { KeycloakAngularModule } from 'keycloak-angular';
import { CarouselModule } from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
   standalone: true,
  imports: [
  CommonModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  
  MatMenuModule,
  
  RouterModule, 
]
})
export class HomeComponent {
selectedLanguage = 'English'; 
 selectedCurrency = 'USD'; 
  selectLanguage(lang: string) {
    this.selectedLanguage = lang;
    // Optionnel : localStorage pour persistance
    localStorage.setItem('selectedLanguage', lang);
  }

  ngOnInit() {
    // Récupérer le choix sauvegardé au chargement
    const lang = localStorage.getItem('selectedLanguage');
    if (lang) this.selectedLanguage = lang;
     // Charger la devise sauvegardée au démarrage
    const curr = localStorage.getItem('selectedCurrency');
    if (curr) this.selectedCurrency = curr;
  }

  

  selectCurrency(curr: string) {
    this.selectedCurrency = curr;
    // Optionnel : sauvegarde le choix dans le navigateur
    localStorage.setItem('selectedCurrency', curr);
  }
}
