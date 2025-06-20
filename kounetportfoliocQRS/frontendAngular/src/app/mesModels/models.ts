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
// ========== AddressDTO ==========
// ===============================
export interface AddressDTO {
  id: number;
  street: string;
  city: string;
  state: string;
  zip: string;
  country: string;
  appartment: number;
  customer: string;
  store: string;
  supplier: string;
  shipping: string;
}

// ===============================
// ========== CategoryDTO =========
// ===============================
export interface CategoryDTO {
  id: string;
  name: string;
}

// ===============================
// ========== CustomerEcommerceDTO ==========
// ===============================
export interface CustomerEcommerceDTO {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
  addressId: string;
  createdAt: string; 
}

// ===============================
// ========== InvoiceDTO ==========
// ===============================
export interface InvoiceDTO {
  id: string;
  orderId: string;
  customerId: string;
  amount: number;
  paymentMethod: string;
  restMonthlyPayment: number;
  paymentStatus: string;
  supplierId: string;
}

// ===============================
// ========== OrderDTO ==========
// ===============================
export interface OrderDTO {
  id: string;
  customerId: string;
  supplierId: string;
  createdAt: string; 
  orderStatus: OrderStatus;
  paymentMethod: string;
  total: number;
  barcode: string;
  shippingId: string;
}

// ===============================
// ========== OrderLineDTO ==========
// ===============================
export interface OrderLineDTO {
  id: string;
  orderId: string;
  stockId: string;
  qty: number;
}

// ===============================
// ========== OrderStatus ==========
// ===============================
export enum OrderStatus {
  Inprogress = "Inprogress",
  Delivered = "Delivered",
  Cancelled = "Cancelled"
}

// ===============================
// ========== OrderStatusDTO ==========
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
// ========== ProductDTO ==========
// ===============================
export interface ProductDTO {
  id: string;
  name: string;
  description: string;
  productSizes: ProductSizeDTO[];
  createdAt: string;
  models: string;
  subcategoryId: string;
  socialGroupId: string;
  isActive: boolean;
}

// ===============================
// ========== ProductSizeDTO ==========
// ===============================
export interface ProductSizeDTO {
  id: string;
size: SizeProd;
  prodId: string;
  price: number;
  pricePromo: number;
  imageUrl: string;
}

// ===============================
// ========== SizeProd (enum) ==========
// ===============================
export enum SizeProd {
  SMALL = "SMALL",
  MEDIUM = "MEDIUM",
  LARGE = "LARGE",
  XL = "XL",
  XXL = "XXL"
}

// ===============================
// ========== ShippingDTO ==========
// ===============================
export interface ShippingDTO {
  id: string;
  orderId: string;
  estimatedDeliveryDate: string;
  shippingDate: string;
  createdAt: string;
  shippingAddressId: string;
  orderStatus: OrderStatus;
}

// ===============================
// ========== SocialGroupDTO ==========
// ===============================
export interface SocialGroupDTO {
  id: string;
  name: string;
  region: string;
  pays: string;
}

// ===============================
// ========== StockDTO ==========
// ===============================
export interface StockDTO {
  id: string;
  designation: string;
  productSizeId: string;
  supplierId: string;
  purchasePrice: number;
  promoPrice: number;
  quantity: number;
  addressId: string;
}

// ===============================
// ========== SubcategoryDTO ==========
// ===============================
export interface SubcategoryDTO {
  id: string;
  name: string;
  categoryId: string;
}

// ===============================
// ========== SupplierDTO ==========
// ===============================
export interface SupplierDTO {
  id: string;
  fullname: string;
  addressId: string;
  email: string;
  personToContact: string;
}