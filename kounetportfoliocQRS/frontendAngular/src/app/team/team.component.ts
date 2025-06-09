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
    { img: 'assets/img/team1.jpg', name: 'Alice', role: 'CEO', desc: 'Expert en stratégie digitale.' },
    { img: 'assets/img/team2.jpg', name: 'Bob', role: 'Lead Dev', desc: 'Codeur fullstack passionné.' },
    { img: 'assets/img/team3.jpg', name: 'Clara', role: 'UI/UX', desc: 'Créative et innovante.' },
  ];
}
