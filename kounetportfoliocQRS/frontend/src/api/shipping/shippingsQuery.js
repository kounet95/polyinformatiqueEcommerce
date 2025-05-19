const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les expéditions (shippings)
export const getAllShippings = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/shippings`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération des expéditions :', error);
    throw error;
  }
};

// Récupérer une expédition par son ID
export const getShippingById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/shippings/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de l\'expédition ${id} :`, error);
    throw error;
  }
};