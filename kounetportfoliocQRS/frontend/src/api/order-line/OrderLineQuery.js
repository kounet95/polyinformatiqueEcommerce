const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les lignes de commande
export const getAllOrderLines = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/orderlines`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de toutes les lignes de commande :', error);
    throw error;
  }
};

// Récupérer une ligne de commande par son ID
export const getOrderLineById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/orderlines/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de la ligne de commande ${id} :`, error);
    throw error;
  }
};