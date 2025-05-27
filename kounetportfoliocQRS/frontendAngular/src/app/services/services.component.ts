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
  selector: 'app-services',
 
  templateUrl: './services.component.html',
  styleUrl: './services.component.css',
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
export class ServicesComponent {
services = [
    { icon: 'build', title: 'Développement', desc: 'Sites web modernes et performants', color: 'primary' },
    { icon: 'brush', title: 'Design', desc: 'Design UI/UX créatif et sur-mesure', color: 'accent' },
    { icon: 'security', title: 'Sécurité', desc: 'Protégez vos données et vos clients', color: 'warn' },
  ];
}
