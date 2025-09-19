import { Component, Input, OnInit } from '@angular/core';
import { LikeService } from '../services/like.service';
import { AuthService } from '../../services/AuthService';
import { CustomerEcommerceDTO, ProductSizeDTO, LikeDTO } from '../../mesModels/models';
import { CustomerService } from '../services/customer.service';
import { ProductSizeService } from '../services/product-size.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AddressFormComponent } from '../address-form/address-form.component';
import { forkJoin } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeModule, MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';

interface ShareNode {
  name: string;
  reseau?: string;
  icon?: string;
  children?: ShareNode[];
}

/** nœud aplati attendu par treeControl / dataSource */
interface FlatNode {
  children: any;
  expandable: boolean;
  name: string;
  level: number;
  reseau?: string;
  icon?: string;
}

@Component({
  selector: 'app-like-product',
  templateUrl: './like-product.component.html',
  styleUrls: ['./like-product.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTreeModule
  ],
})
export class LikeProductComponent implements OnInit {
  message = '';
  loading = false;
  nbreLikes: number | null = null;
  likedProductSizeDTOs: ProductSizeDTO[] = [];
  isLiked = false;
  likeCount = 0;
  productLikes: LikeDTO[] = [];

  customer: CustomerEcommerceDTO = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    addressId: '',
    createdAt: '',
  };

  @Input() productSizeId!: string;

  constructor(
    private likeService: LikeService,
    private authService: AuthService,
    private customerService: CustomerService,
    private productSizeService: ProductSizeService,
    private router: Router
  ) {
    // dataSource initée après déclaration des TREE_DATA (en bas)
    this.dataSource.data = this.TREE_DATA;
  }

  ngOnInit(): void {
    this.loadLikesCount();
    this.checkIfLiked();
    this.loadCustomer();
    this.loadLikedProductSizes();
    this.loadProductLikes();
  }

  loadLikedProductSizes() {
    const customerId = this.authService.getUserId();
    if (customerId) {
      this.likeService.getLikesByCustomer(customerId).subscribe((likes: LikeDTO[]) => {
        const ids = likes
          .map((like) => (like as any).productId || (like as any).product)
          .filter((id, idx, arr) => !!id && arr.indexOf(id) === idx);

        this.nbreLikes = ids.length;

        if (ids.length === 0) {
          this.likedProductSizeDTOs = [];
          return;
        }

        const observables = ids.map((id) => this.productSizeService.getProductSizeById(id));
        forkJoin(observables).subscribe((productSizes) => {
          this.likedProductSizeDTOs = productSizes;
        });
      });
    }
  }

  private loadProductLikes() {
    if (this.productSizeId) {
      this.likeService.getLikesByProduct(this.productSizeId).subscribe((likes) => {
        this.productLikes = likes;
      });
    }
  }

  loadCustomer() {
    const userId = this.authService.getUserId();
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: (customer) => {
          if (customer) this.customer = customer;
        },
        error: () => {
          this.message = 'Impossible de charger les informations client.';
        },
      });
    }
  }

  like(): void {
    if (!this.productSizeId) return;
    this.likeService.likeProduct(this.productSizeId).subscribe(() => {
      this.isLiked = true;
      this.loadLikesCount();
      this.loadProductLikes();
      this.loadLikedProductSizes();
    });
  }

  unlike(productSizeId?: string): void {
    const id = productSizeId ?? this.productSizeId;
    if (!id) return;

    this.likeService.unlikeProduct(id).subscribe(() => {
      if (id === this.productSizeId) this.isLiked = false;
      this.loadLikesCount();
      this.loadProductLikes();
      this.loadLikedProductSizes();
    });
  }

  private loadLikesCount(): void {
    if (!this.productSizeId) return;
    this.likeService.countLikesByProduct(this.productSizeId).subscribe((count) => {
      this.likeCount = count;
    });
  }

  private checkIfLiked(): void {
    const customerId = this.authService.getUserId();
    if (customerId) {
      this.likeService
        .checkCustomerLiked(this.productSizeId, customerId)
        .subscribe((isLiked) => (this.isLiked = isLiked));
    }
  }

  viewProduct(sizeId: string) {
    this.router.navigate([`/product/${sizeId}`]);
  }

  partage(reseau: string, produit?: ProductSizeDTO) {
    const url = encodeURIComponent(`${window.location.origin}/product/${produit?.id ?? this.productSizeId}`);
    const text = encodeURIComponent(
      `${produit?.product?.name || 'Découvrez ce produit'} - ${produit?.sizeProd || ''} - ${produit?.price || ''} CAD`
    );
    let shareUrl = '';

    switch (reseau) {
      case 'facebook':
        shareUrl = `https://www.facebook.com/sharer/sharer.php?u=${url}&quote=${text}`;
        break;
      case 'twitter':
        shareUrl = `https://twitter.com/intent/tweet?url=${url}&text=${text}`;
        break;
      case 'whatsapp':
        shareUrl = `https://wa.me/?text=${text}%20${url}`;
        break;
      case 'linkedin':
        shareUrl = `https://www.linkedin.com/shareArticle?mini=true&url=${url}&title=${text}`;
        break;
    }

    if (shareUrl) window.open(shareUrl, '_blank');
  }

 
private transformer = (node: ShareNode, level: number): FlatNode => {
  return {
    expandable: !!node.children && node.children.length > 0,
    name: node.name,
    reseau: node.reseau,
    icon: node.icon,
    level,
    children: undefined
  };
};

  treeControl = new FlatTreeControl<FlatNode>((node) => node.level, (node) => node.expandable);

  treeFlattener = new MatTreeFlattener<ShareNode, FlatNode>(
    this.transformer,
    (node) => node.level,
    (node) => !!node.children && node.children.length > 0,
    (node) => node.children
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: FlatNode) => node.expandable;

  TREE_DATA: ShareNode[] = [
    {
      name: 'Partager',
      children: [
        { name: 'Facebook', reseau: 'facebook', icon: 'icons8-facebook-48.png' },
        { name: 'WhatsApp', reseau: 'whatsapp', icon: 'icons8-whatsapp-48.png' },
        { name: 'LinkedIn', reseau: 'linkedin', icon: 'icons8-linkedin-48.png' },
        { name: 'Instagram', reseau: 'instagram', icon: 'icons8-instagram-48.png' },
      ],
    },
  ];
}
