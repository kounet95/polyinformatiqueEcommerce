const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer toutes les sous-catégories
export const getAllSubcategories = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/subcategories`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de toutes les sous-catégories :', error);
    throw error;
  }
};

// Récupérer une sous-catégorie par ID
export const getSubcategoryById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/subcategories/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de la sous-catégorie ${id} :`, error);
    throw error;
  }
};