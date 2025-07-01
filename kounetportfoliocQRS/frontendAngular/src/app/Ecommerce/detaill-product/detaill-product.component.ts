import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommentModel, ProductDTO, ProductSizeDTO } from '../../../app/mesModels/models';
import { ProductService } from '../../../app/Ecommerce/services/produit.service';
import { CartService } from '../../../app/Ecommerce/services/cartservice';
import { ProductSizeService } from '../services/product-size.service';
import { CommentService } from '../services/commentaire.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './detaill-product.component.html',
  styleUrls: ['./detaill-product.component.css'],
  standalone: false,
})
export class ProductDetailsComponent implements OnInit {
  product: ProductDTO | null = null;
  productSize: ProductSizeDTO []=[];
  commentaire: CommentModel[]=[];
  quantity: number = 1;
  addedMessage: string = '';

 images = [
  'assets/img/01_Home.png', 
  'assets/img/p1.jpeg', 
  'assets/img/p2.jpeg', 
  'assets/img/p2.jpeg'  
];
  selectedImageIndex = 0;

  colors = [
    { value: 'black', name: 'Black', selected: true },
    { value: 'gray', name: 'Gray', selected: false },
    { value: 'blue', name: 'Blue', selected: false },
    { value: 'pink', name: 'Pink', selected: false }
  ];
  sizes = [
    { label: 'S', value: 'S' },
    { label: 'M', value: 'M' },
    { label: 'L', value: 'L' }
  ];
  selectedSize = 'M';

  // Exemple reviews
  reviews = [
    {author: 'John Doe', date: '21/04/2024', rating: 5, text: 'Exceptional sound quality and comfort.'},
    {author: 'Jane Smith', date: '19/04/2024', rating: 4, text: 'Great headphones, battery could be better.'},
    {author: 'Michael Johnson', date: '12/04/2024', rating: 5, text: 'Impressive noise cancellation.'}
  ];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private commentaireSevice:CommentService,
    private cartService: CartService
  ) {}

 ngOnInit(): void {
  const id = this.route.snapshot.paramMap.get('id');
  if (id) {
    this.productService.getProductById(id).subscribe(prod => {
      this.product = prod;
    });

    this.productSizeService.getProductSizesByProductId(id).subscribe(sizes => {
      this.productSize = sizes;
    });
    /*this.commentaireSevice.getCommentairesByProductId(id).subscribe(coms => {
      this.commentaire = coms;
    });*/
  } else {
    alert("vous devez selectionne un produit");
  }

  
}

  selectImage(idx: number) {
    this.selectedImageIndex = idx;
  }

  selectColor(idx: number) {
    this.colors.forEach((c, i) => c.selected = i === idx);
  }

  selectSize(size: string) {
    this.selectedSize = size;
  }

  incrementQty() {
    this.quantity++;
  }

  decrementQty() {
    if (this.quantity > 1) this.quantity--;
  }

  addToCart() {
    if (!this.product) return;
    this.addedMessage = 'Produit ajouté au panier !';
    setTimeout(() => (this.addedMessage = ''), 1500);
  }

  // Getter pour la couleur sélectionnée (évite la fonction dans le template)
  get selectedColorName(): string {
    const selected = this.colors.find(c => c.selected);
    return selected ? selected.name : '';
  }

  // Pour itérer sur un nombre dans *ngFor
  get fiveStars() {
    return [1,2,3,4,5];
  }
}