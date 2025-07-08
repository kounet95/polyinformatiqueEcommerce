import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO, ProductSizeDTO, SocialGroupDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CartService } from '../services/cartservice';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CategoriesocialesService } from '../services/categoriesociales.service';

type CategoryWithChildren = CategoryDTO & { children?: { id: string; name: string }[] };

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryWithChildren[] = [];
  products: ProductDTO[] = [];
  displayedColumns = ["id", "sizeProd", "price", "pricePromo"];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';
  productSizes: ProductSizeDTO[] = [];
  socialGroups: string[] = [];
  datasource: any;
 page: number = 0;
  size: number = 10;
  totalElements: number = 0;
  filters = {
    selectedCategoryId: null as string | null,
    selectedCouleurs: [] as string[],
    selectedSouscategorie: [] as string[],
    selectedSocialGroup: null as string | null,
    selectedProductSize: null as string | null,
    searchKeyword: '',
    selectedPriceRange: '',
    sortOption: 'featured',
    viewMode: 'grid' as 'grid' | 'list',
    itemsPerPage: 12,
    onSale: false
  };

  activeFilters: { key: string, label: string }[] = [];

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private sousCategorieService: SouscategoriesService,
    private cartService: CartService,
    private snackBar: MatSnackBar,
    private router: Router,
    private socialGroupService: CategoriesocialesService 
  ) {}

  ngOnInit(): void {
    this.fetchCategoriesAndSubcategories();
    this.fetchFilteredProductSizes();
    this.datasource = new MatTableDataSource();
    this.loadProductSizes();
    this.loadingSocialGroups();
  }


   loadProductSizes(): void {
  this.loading = true;

  // 1) Chargement les tailles
  this.productSizeService.getAllProductsise(this.page, this.size).subscribe({
    next: (result: any) => {
      let sizes: ProductSizeDTO[] = [];

      if (Array.isArray(result)) {
        sizes = result;
        this.totalElements = result.length;
      } else {
        sizes = result.content ?? [];
        this.totalElements = result.totalElements ?? 0;
      }

      // 2) Chargement les produits après
      this.productService.getAllProducts().subscribe({
        next: (response) => {
          const products: ProductDTO[] = response.content || [];

          this.productSizes = sizes.map(size => {
      const matchedProduct = products.find(p => p.id === size.prodId);

      return {
        ...size,
        product: matchedProduct ?? {
          id: size.prodId || 'unknown',
          name: matchedProduct 
            || size.product?.name 
            || `Product ${size.id?.substring(0, 5) || 'N/A'}`,
          description: matchedProduct || 'No description',
          createdAt: new Date().toISOString(),
          models: size.frontUrl || '',
          subcategoryId: '',
          socialGroupId: '',
          isActive: true
        }
      };
    });
          this.loading = false;
        },
        error: () => {
          this.error = 'Erreur lors du chargement des produits.';
          this.loading = false;
        }
      });

    },
    error: () => {
      this.error = 'Erreur lors du chargement des tailles de produit.';
      this.loading = false;
    }
  });
}

  get totalPages(): number {
  return Math.ceil(this.totalElements / this.size) || 1;
}

  nextPage(): void {
    if ((this.page + 1) * this.size < this.totalElements) {
      this.page++;
      this.loadProductSizes();
    }
  }

  prevPage(): void {
     if (this.page > 0) {
      this.page--;
      this.loadProductSizes();
    }
  }
  fetchCategoriesAndSubcategories(): void {
    this.loading = true;
    this.categoryService.getAllCategories().subscribe({
      next: (cats) => {
        this.sousCategorieService.getAllSubcategories().subscribe({
          next: (sousCats) => {
            this.categories = cats.map(cat => ({
              ...cat,
              children: sousCats
                .filter(sc => sc.categoryId === cat.id)
                .map(sc => ({ id: sc.id, name: sc.name }))
            }));
            this.loading = false;
          },
          error: () => {
            this.error = "Erreur lors du chargement des sous-catégories.";
            this.loading = false;
          }
        });
      },
      error: () => {
        this.error = "Erreur lors du chargement des catégories.";
        this.loading = false;
      }
    });
  }

  fetchFilteredProductSizes(): void {
    this.loading = true;

    let minPromo: number | undefined = undefined;
    let maxPromo: number | undefined = undefined;

    if (this.filters.selectedPriceRange) {
      const parts = this.filters.selectedPriceRange.split('-');
      if (parts.length === 2) {
        minPromo = parseFloat(parts[0]);
        maxPromo = parseFloat(parts[1]);
      } else if (this.filters.selectedPriceRange.endsWith('+')) {
        minPromo = parseFloat(this.filters.selectedPriceRange.replace('+', ''));
      }
    }

    const onSale = this.filters.onSale ? true : undefined;

    // On prend la première sous-catégorie uniquement, sinon undefined
    const subcategoryId = this.filters.selectedSouscategorie.length > 0 ?
      this.filters.selectedSouscategorie[0] : undefined;

    this.productSizeService.searchProductSizes(
      this.filters.searchKeyword,
      minPromo,
      maxPromo,
      this.filters.selectedProductSize ?? undefined,
      onSale,
      undefined,
      subcategoryId,
      this.filters.selectedSocialGroup ?? undefined,
    ).subscribe({
      next: (sizes: ProductSizeDTO[]) => {
        // Log the first size object to see its structure
        if (sizes.length > 0) {
          console.log('First size object:', sizes[0]);
        }

        this.productService.getAllProducts().subscribe({
          next: (response) => {
            const products: ProductDTO[] = response.content || [];

            // Log the first product to see its structure
            if (products.length > 0) {
              console.log('First product object:', products[0]);
            }

            this.productSizes = sizes.map(size => {
              // Try to find the product using prodId
              let matchedProduct = products.find(p => p.id === size.prodId);

              // If no match found and we have products, create a default product
              if (!matchedProduct && products.length > 0) {
                matchedProduct = {
                  id: size.prodId || 'unknown',
                  name: 'Product ' + (size.id || '').substring(0, 5),
                  description: 'Product description',
                  createdAt: new Date().toISOString(),
                  models: size.frontUrl || '',
                  subcategoryId: '',
                  socialGroupId: '',
                  isActive: true
                };
              }

              return {
                ...size,
                product: matchedProduct
              };
            });

            if (this.filters.sortOption === 'priceAsc') {
              this.productSizes.sort((a, b) => (a.pricePromo || a.price) - (b.pricePromo || b.price));
            } else if (this.filters.sortOption === 'priceDesc') {
              this.productSizes.sort((a, b) => (b.pricePromo || b.price) - (a.pricePromo || a.price));
            }

            this.datasource = new MatTableDataSource(this.productSizes);
            this.loading = false;
            this.updateActiveFilters();
          },
          error: () => {
            this.error = "Erreur lors du chargement des produits.";
            this.loading = false;
          }
        });
      },
      error: () => {
        this.error = "Erreur lors de la recherche des tailles.";
        this.loading = false;
      }
    });
  }

  updateActiveFilters(): void {
    this.activeFilters = [];

    if (this.filters.searchKeyword) {
      this.activeFilters.push({ key: 'search', label: `Recherche: ${this.filters.searchKeyword}` });
    }

    if (this.filters.selectedPriceRange) {
      this.activeFilters.push({
        key: 'price',
        label: `Prix: ${this.filters.selectedPriceRange.replace('-', ' à ').replace('+', '+')} €`
      });
    }

    if (this.filters.selectedCategoryId) {
      const cat = this.categories.find(c => c.id === this.filters.selectedCategoryId);
      if (cat) {
        this.activeFilters.push({ key: 'category', label: `Catégorie: ${cat.name}` });
      }
    }

    if (this.filters.selectedSouscategorie.length > 0) {
      const subcats = this.filters.selectedSouscategorie.map(id => {
        for (const cat of this.categories) {
          const subcat = cat.children?.find(sc => sc.id === id);
          if (subcat) return subcat.name;
        }
        return id;
      });
      this.activeFilters.push({ key: 'subcategory', label: `Sous-catégorie: ${subcats.join(', ')}` });
    }

    if (this.filters.selectedCouleurs.length > 0) {
      this.activeFilters.push({ key: 'colors', label: `Couleurs: ${this.filters.selectedCouleurs.join(', ')}` });
    }

    if (this.filters.selectedSocialGroup) {
      this.activeFilters.push({ key: 'socialGroup', label: `Groupe social: ${this.filters.selectedSocialGroup}` });
    }

    if (this.filters.selectedProductSize) {
      this.activeFilters.push({ key: 'size', label: `Taille: ${this.filters.selectedProductSize}` });
    }

    if (this.filters.onSale) {
      this.activeFilters.push({ key: 'sale', label: `Soldes uniquement` });
    }
  }

  onCategorySelect(categoryId: string): void {
    this.filters.selectedCategoryId = categoryId;
    this.filters.selectedSouscategorie = this.categories.find(c => c.id === categoryId)?.children?.map(child => child.id) || [];
    this.applyFilters();
  }

  applyFilters(): void {
    this.fetchFilteredProductSizes();
  }

  removeFilter(key: string): void {
    if (key === 'search') this.filters.searchKeyword = '';
    if (key === 'price') this.filters.selectedPriceRange = '';
    if (key === 'category') this.filters.selectedCategoryId = null;
    if (key === 'subcategory') this.filters.selectedSouscategorie = [];
    if (key === 'colors') this.filters.selectedCouleurs = [];
    if (key === 'socialGroup') this.filters.selectedSocialGroup = null;
    if (key === 'size') this.filters.selectedProductSize = null;
    if (key === 'sale') this.filters.onSale = false;

    this.applyFilters();
  }

  clearAllFilters(): void {
    this.filters.searchKeyword = '';
    this.filters.selectedPriceRange = '';
    this.filters.sortOption = 'featured';
    this.filters.itemsPerPage = 12;
    this.filters.selectedCategoryId = null;
    this.filters.selectedSouscategorie = [];
    this.filters.selectedSocialGroup = null;
    this.filters.selectedProductSize = null;
    this.filters.selectedCouleurs = [];
    this.filters.onSale = false;
    this.activeFilters = [];
    this.fetchFilteredProductSizes();
  }

  clearAllColors(): void {
    this.filters.selectedCouleurs = [];
    this.fetchFilteredProductSizes();
  }

  applyColorFilters(): void {
    this.fetchFilteredProductSizes();
  }

  onCouleurCheckboxChange(couleur: string, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    this.onCouleurChange(couleur, checked);
  }

  onCouleurChange(couleur: string, checked: boolean): void {
    if (checked && !this.filters.selectedCouleurs.includes(couleur)) {
      this.filters.selectedCouleurs.push(couleur);
    } else if (!checked) {
      this.filters.selectedCouleurs = this.filters.selectedCouleurs.filter(c => c !== couleur);
    }
    this.fetchFilteredProductSizes();
  }

  onSocialGroupChange(sgId: string): void {
    this.filters.selectedSocialGroup = sgId;
    this.fetchFilteredProductSizes();
  }

  onProductSizeChange(size: string): void {
    this.filters.selectedProductSize = size;
    this.fetchFilteredProductSizes();
  }

  onProductSouscategorieChange(id: string): void {
    if (this.filters.selectedSouscategorie.includes(id)) {
      this.filters.selectedSouscategorie = this.filters.selectedSouscategorie.filter(c => c !== id);
    } else {
      this.filters.selectedSouscategorie.push(id);
    }
    this.fetchFilteredProductSizes();
  }

  onMobileSearch(): void {
    this.filters.searchKeyword = this.mobileSearch;
    this.applyFilters();
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

  getProductImageUrl(models: string): string {
    return models ?? 'assets/img/placeholder.png';
  }

  addCart(size: ProductSizeDTO) {
    // Ensure product information is available
    if (!size.product) {
      size.product = {
        id: size.prodId || 'unknown',
        name: 'Product ' + (size.id || '').substring(0, 5),
        description: 'Product description',
        createdAt: new Date().toISOString(),
        models: size.frontUrl || '',
        subcategoryId: '',
        socialGroupId: '',
        isActive: true
      };
    }

    this.cartService.addToCart(size, 1);
    this.snackBar.open(
      `Ajouté: ${size.product.name} - ${size.sizeProd || 'Standard'} au panier !`,
      'Fermer',
      { duration: 2000, verticalPosition: 'top' }
    );
  }

voirDetaille(sizeId: string) {
  this.router.navigate([`/product/${sizeId}`]);
}

  get allFilters(): Record<string, any> {
    return { ...this.filters };
  }

  loadingSocialGroups(){
    this.loading = true;

    this.socialGroupService.getAllSocialGroups().subscribe({
      next: (socialGroups) => {
        this.filters.selectedSocialGroup = socialGroups.length > 0 ? socialGroups[0].id : null;
        this.loading = false;
        this.socialGroups = socialGroups.map(group => group.name);
        console.log('Social Groups:', this.socialGroups);
      },
      error: () => {
        this.error = "Erreur lors du chargement des groupes sociaux.";
        this.loading = false;
      }
    });
   
  }
}
