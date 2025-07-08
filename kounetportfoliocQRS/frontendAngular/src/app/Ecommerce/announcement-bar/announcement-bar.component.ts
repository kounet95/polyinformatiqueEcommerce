import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';



@Component({
  selector: 'app-announcement-bar',
  templateUrl: './announcement-bar.component.html',
  styleUrls: ['./announcement-bar.component.css'],
  standalone: true,
   
})
export class AnnouncementBarComponent implements OnInit, OnDestroy {
  announcements = [
    "🚚 Livraison gratuite dès 50€",
    "🎁 -20% sur votre première commande",
    "💰 30 jours satisfait ou remboursé",
    "⚡ Ventes flash jusqu'à -70% !"
  ];

  currentIndex = 0;
  intervalId: any;

  ngOnInit() {
    this.intervalId = setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.announcements.length;
    }, 4000);
  }

  ngOnDestroy() {
    clearInterval(this.intervalId);
  }
}