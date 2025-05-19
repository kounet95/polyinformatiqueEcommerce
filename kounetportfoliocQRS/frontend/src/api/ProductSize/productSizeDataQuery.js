const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les tailles de produit
export const getAllProductSizes = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/productsizes`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de toutes les tailles de produit :', error);
    throw error;
  }
};

// Récupérer une taille de produit par ID
export const getProductSizeById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/productsizes/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de la taille de produit ${id} :`, error);
    throw error;
  }
};