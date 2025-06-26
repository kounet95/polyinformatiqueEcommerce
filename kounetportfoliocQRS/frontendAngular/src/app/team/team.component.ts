import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrl: './team.component.css',
    standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
    CommonModule,         
    MatCardModule,        
   
  ]
})
export class TeamComponent {
team = [
    { img: 'assets/img/person-m-1.webp', name: 'Arsene', role: 'Dev , Formateur TI', desc: 'CCNA, AZ900.' },
    { img: 'assets/img/person-m-9.webp', name: 'Oumar', role: ' Dev, Formateur TI', desc: 'CCNA, LIPC.' },
  ];
}
