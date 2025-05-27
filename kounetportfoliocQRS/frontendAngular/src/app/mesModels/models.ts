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
  createdAt: string; // LocalDate -> string (ISO date)
  authorId: string;
  itemId: string;
}

// ===============================
// ========== DOMAIN =============
// ===============================

export interface DomainModel {
  id: string;
  name: string;
  articles: string[]; // List<String>
}

// ===============================
// ========== EVENT ==============
// ===============================

export interface EventModel {
  id: string;
  location: string;
  begin: string;      // LocalDateTime -> string (ISO date-time)
  end: string;        // LocalDateTime -> string (ISO date-time)
  content: string;
  urlMedia: string;
  title: string;
  createdAt: string;  // LocalDate -> string (ISO date)
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
  createdAt: string;      // LocalDate -> string (ISO date)
  authorId: string;
  mediaIds: string[];     // List<String>
  commentIds: string[];   // List<String>
  tagIds: string[];       // List<String>
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
  createdAt: string;    // LocalDate -> string (ISO)
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
  id: string;             // Identifiant unique (peut être null ou vide)
  orderId: string;        // ID de la commande (obligatoire)
  barcode: string;        // Code-barres (obligatoire)
  status: string;         // Statut de la commande (obligatoire)
  updatedAt: string;      // Dernière mise à jour (date-heure ISO, obligatoire)
  customerId: string;     // ID du client (obligatoire)
  customerName: string;   // Nom du client (obligatoire)
}