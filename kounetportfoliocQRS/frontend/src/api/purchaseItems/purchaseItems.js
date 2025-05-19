const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer tous les items d'achat
export const getAllPurchaseItems = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/purchaseitems`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de tous les items d\'achat :', error);
    throw error;
  }
};

// Récupérer un item d'achat par ID
export const getPurchaseItemById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/purchaseitems/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de l'item d'achat ${id} :`, error);
    throw error;
  }
};