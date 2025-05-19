const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les commandes
export const getAllOrders = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/orders`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de toutes les commandes :', error);
    throw error;
  }
};

// Récupérer une commande par son ID
export const getOrderById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/orders/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de la commande ${id} :`, error);
    throw error;
  }
};