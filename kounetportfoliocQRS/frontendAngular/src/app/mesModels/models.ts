// ===============================
// ========== Article ================
// ===============================

export interface ArticleModel {
  id: string;
  content: string;
  urlMedia: string;
  title: string;
  createdAt: string; 
  authorId: string;
  domainId: string;
  tagIds: string[];
  commentIds: string[];
}

// ===============================
// ========== COMMENT ============
// ===============================

export interface CommentModel {
  id: string;
  contenu: string;
  createdAt: string; 
  authorId: string;
  itemId: string;
}

// ===============================
// ========== DOMAIN =============
// ===============================

export interface DomainModel {
  id: string;
  name: string;
  articles: string[]; 
}

// ===============================
// ========== EVENT ==============
// ===============================

export interface EventModel {
  id: string;
  location: string;
  begin: string;      
  end: string;        
  content: string;
  urlMedia: string;
  title: string;
  createdAt: string;  
  authorId: string;
  domainId: string;
  tagIds: string[];
  commentIds: string[];
}

// ===============================
// ========== ITEM ===============
// ===============================

export interface ItemModel {
  id: string;
  content: string;
  urlMedia: string;
  title: string;
  createdAt: string;      
  authorId: string;
  mediaIds: string[];    
  commentIds: string[];   
  tagIds: string[];       
}

// ===============================
// ========== NEWS ===============
// ===============================

export interface NewsModel {
  id: string;
  summary: string;
  content: string;
  urlMedia: string;
  title: string;
  createdAt: string;   
  authorId: string;
  domainId: string;
  tagIds: string[];
  commentIds: string[];
}

// ===============================
// ========== TAG ================
// ===============================

export interface TagModel {
  id: string;
  name: string;
  itemIds: string[];
}

// ===============================
// ====== OrderStatusDTO =========
// ===============================

export interface OrderStatusDTO {
  id: string;           
  orderId: string;       
  barcode: string;       
  status: string;         
  updatedAt: string;     
  customerId: string;     
  customerName: string;   
}


// ===============================
// ========== CategoryDTO =========
// ===============================

export interface CategoryDTO {
  id: string;
  name: string;
  description?: string;
  imageUrl?: string;
  parentId?: number;
  children?: CategoryDTO[];
}

// ===============================
// ========== ProductDTO =========
// ===============================

export interface ProductDTO {
  id: string;
  name: string;
  description: string;
  price: number;
  createdAt: string;  
  closedAt?: string;
  subcategoryId: string;
  socialGroupId: string;
  imageUrl: string;
  isActive: boolean;
  couleurs?: string; 
 productSize: ProductSize;
}

// ===============================
// ========== ProductSizeDTO =====
// ===============================
export enum ProductSize {
  SMALL = "SMALL",
  MEDIUM = "MEDIUM",
  LARGE = "LARGE",
  XL = "XL",
  XXL = "XXL"
}

// ===============================
// ========== PurchaseDTO =========
// ===============================

export interface PurchaseDTO {
  id: string;
  supplierId: string;
  createdAt: string;
  status: string;
  total: number;
}

// ===============================
// ======== PurchaseItemDTO =======
// ===============================

export interface PurchaseItemDTO {
  id: string;
  purchaseId: string;
  productId: string;
  qty: number;
  unitPrice: number;
}

// ===============================
// ========== ShippingDTO =========
// ===============================

export interface ShippingDTO {
  id: string;
  orderId: string;
  deliveryStatus: string;
  estimatedDeliveryDate: string;
  shippingDate: string;
  shippingAddress: string;
}

// ===============================
// ========== SocialGroupDTO ======
// ===============================

export interface SocialGroupDTO {
  id: string;
  name: string;
  region: string;
  country: string;
}

// ===============================
// =========== StockDTO ===========
// ===============================

export interface StockDTO {
  id: string;
  productSizeId: string;
  supplierId: string;
  purchasePrice: number;
  promoPrice: number;
  salePrice: number;
  stockAvailable: number;
  quantity: number;
}

// ===============================
// ========== SubcategoryDTO ======
// ===============================

export interface SubcategoryDTO {
  id: string;
  name: string;
  categoryId: string;
}

// ===============================
// ========== SupplierDTO =========
// ===============================

export interface SupplierDTO {
  id: string;
  fullname: string;
  city: string;
  email: string;
  personToContact: string;
}

// ===============================
// ========== OrderLineDTO ========
// ===============================

export interface OrderLineDTO {
  id: string;
  orderId: string;
  productSizeId: string;
  qty: number;
}

// ===============================
// ============ OrderDTO ==========
// ===============================

export interface OrderDTO {
  id: string;
  customerId: string;
  createdAt: string;
  orderStatus: string;
  paymentMethod: string;
  total: number;
  barcode: string;
}

// ===============================
// =========== InvoiceDTO =========
// ===============================

export interface InvoiceDTO {
  invoiceId: string;
  orderId: string;
  amount: number;
  paymentStatus: string;
}

// ===============================
// ===== CustomerEcommerceDTO =====
// ===============================

export interface CustomerEcommerceDTO {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
  shippingAddress: string;
  billingAddress: string;
  createdAt: string; // ISO 8601 string (LocalDateTime in Java)
}