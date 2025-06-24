import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // <= nécessaire pour ngFor et ngIf

@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.css']
})
export class FaqComponent {

  faqList = [
    {
      title: 'Quels sont les délais de livraison pour les commandes LeContinent ?',
      answer: 'Les délais de livraison varient entre 3 à 7 jours ouvrables selon votre région. Une confirmation vous sera envoyée dès l’expédition.'
    },
    {
      title: 'Comment puis-je suivre ma commande ?',
      answer: 'Une fois votre commande expédiée, vous recevrez un lien de suivi par email. Vous pouvez également consulter le statut depuis votre compte.'
    },
    {
      title: 'Puis-je retourner un article qui ne me convient pas ?',
      answer: 'Oui, vous avez 14 jours après réception pour effectuer un retour. L’article doit être dans son état d’origine avec l’étiquette.'
    },
    {
      title: 'Proposez-vous des tailles personnalisées ?',
      answer: 'Oui, LeContinent offre des options de tailles sur mesure pour certains articles. Contactez notre service client pour plus d’informations.'
    },
    {
      title: 'Quels sont les moyens de paiement acceptés ?',
      answer: 'Nous acceptons les paiements par carte bancaire, PayPal, Mobile Money et virements bancaires.'
    }
  ];
}
