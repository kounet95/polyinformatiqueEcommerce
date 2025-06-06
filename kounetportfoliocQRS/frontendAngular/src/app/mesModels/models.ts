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