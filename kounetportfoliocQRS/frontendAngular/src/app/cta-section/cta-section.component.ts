import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-cta-section',
  templateUrl: './cta-section.component.html',
  styleUrls: ['./cta-section.component.css'],
   standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    CommonModule,         
    MatCardModule,        
   
  ]
})
export class CtaSectionComponent {
  examples = [
    { image: 'assets/imag/robe-africaine.jpg', label: 'Robe Africaine' },
    { image: 'assets/imag/costume-homme.jpg', label: 'Costume Homme' },
    { image: 'assets/imag/boubou.jpg', label: 'Boubou Royal' },
    { image: 'assets/imag/accessoire.jpg', label: 'Accessoires Artisanaux' }
  ];

}