export interface PageResponse<T> {
  content: T[];         // La liste des éléments de la page courante
  totalElements: number; // Nombre total d’éléments dans la base de données
  totalPages: number;    // Nombre total de pages
  pageNumber: number;    // Numéro de la page courante (commence à 0 ou 1 selon ton backend)
  pageSize: number;      // Taille de la page (nombre d’éléments par page)
}