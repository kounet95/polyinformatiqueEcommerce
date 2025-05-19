const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les réceptions d'achats
export const getAllPurchases = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/purchases`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de toutes les réceptions d\'achats :', error);
    throw error;
  }
};

// Récupérer une réception d'achat par ID
export const getPurchaseById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/purchases/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de la réception d'achat ${id} :`, error);
    throw error;
  }
};