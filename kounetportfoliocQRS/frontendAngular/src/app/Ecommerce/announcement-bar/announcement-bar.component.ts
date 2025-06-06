import { Component } from '@angular/core';
import { SwiperConfigInterface, SwiperModule } from 'ngx-swiper-wrapper';

@Component({
  selector: 'app-announcement-bar',
  standalone: true,
  imports: [SwiperModule],
  templateUrl: './announcement-bar.component.html',
  styleUrls: ['./announcement-bar.component.css']
})
export class AnnouncementBarComponent {
  announcements = [
    "🚚 Free shipping on orders over $50",
    "💰 30 days money back guarantee",
    "🎁 20% off on your first order - Use code: FIRST20",
    "⚡ Flash Sale! Up to 70% off on selected items"
  ];

  config: SwiperConfigInterface = {
    loop: true,
    autoplay: {
      delay: 4000,
      disableOnInteraction: false
    }
  };
}
