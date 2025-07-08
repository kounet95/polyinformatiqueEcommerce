import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommentModel, ProductDTO, ProductSizeDTO } from '../../../app/mesModels/models';
import { ProductService } from '../../../app/Ecommerce/services/produit.service';
import { CartService } from '../../../app/Ecommerce/services/cartservice';
import { ProductSizeService } from '../services/product-size.service';
import { CommentService } from '../services/commentaire.service';
import { LikeService } from '../services/like.service';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-product-details',
  templateUrl: './detaill-product.component.html',
  styleUrls: ['./detaill-product.component.css'],
  standalone: false,
})
export class ProductDetailsComponent implements OnInit {

  product: ProductDTO | null = null;
  productSize: ProductSizeDTO | null = null;
  commentaire: CommentModel[] = [];

  quantity = 1;
  addedMessage = '';

  selectedImageIndex = 0;

  colors = [
    { value: 'black', name: 'Noir', selected: true },
    { value: 'gray', name: 'Gris', selected: false },
    { value: 'blue', name: 'Bleu', selected: false },
    { value: 'pink', name: 'Rose', selected: false }
  ];

  selectedSize = ''; // taille dynamique

  likeCount = 0;
  liked = false;

  reviews = [
    { author: 'John Doe', date: '21/04/2024', rating: 5, text: 'Exceptional sound quality and comfort.' },
    { author: 'Jane Smith', date: '19/04/2024', rating: 4, text: 'Great headphones, battery could be better.' },
    { author: 'Michael Johnson', date: '12/04/2024', rating: 5, text: 'Impressive noise cancellation.' }
  ];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private commentaireService: CommentService,
    private cartService: CartService,
    private likeService: LikeService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productSizeService.getProductSizeById(id).subscribe(size => {
        this.productSize = size;
        console.log('ProductSize:', size);

        this.selectedSize = size.sizeProd;

        const prodIdToUse = size.prodId || '4b624571-6347-4e94-a606-7fc91fd7f5b2';
        if (prodIdToUse) {
          this.productService.getProductById(prodIdToUse).subscribe(prod => {
            this.product = prod;
            console.log('Product:', prod);

            this.loadLikesCount();
            this.checkIfLiked();
          });
        } else {
          console.warn('prodId is null, cannot load product');
        }
      });

    } else {
      alert('Vous devez sélectionner un produit');
    }

    const sizeId = this.route.snapshot.paramMap.get('id');
    console.log('ProductSize.id:', sizeId);
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
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    if (!this.product) return;

    this.addedMessage = 'Produit ajouté au panier !';
    // this.cartService.addToCart({...});
    setTimeout(() => (this.addedMessage = ''), 1500);
  }

  toggleLike() {
    const customerId = this.authService.getUserId();
    if (!this.productSize?.id || !customerId) {
      alert("Utilisateur non connecté");
      return;
    }

    if (this.liked) {
      this.likeService.unlikeProduct(this.productSize.id).subscribe(() => {
        this.liked = false;
        this.loadLikesCount();
      });
    } else {
      this.likeService.likeProduct(this.productSize.id).subscribe(() => {
        this.liked = true;
        this.loadLikesCount();
      });
    }
  }

  private loadLikesCount() {
    if (!this.productSize?.prodId) return;
    this.likeService.countLikesByProduct(this.productSize.id).subscribe(count => {
      this.likeCount = count;
    });
  }

  private checkIfLiked() {
    const customerId = this.authService.getUserId();
    if (!this.productSize || !customerId) return;
    this.likeService.checkCustomerLiked(this.productSize.id, customerId).subscribe(isLiked => {
      this.liked = isLiked;
    });
  }

  get selectedColorName(): string {
    const selected = this.colors.find(c => c.selected);
    return selected ? selected.name : '';
  }

  get fiveStars() {
    return [1, 2, 3, 4, 5];
  }
}
