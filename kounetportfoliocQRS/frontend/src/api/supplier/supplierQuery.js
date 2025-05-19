const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer les fournisseurs avec pagination
export const getAllSuppliers = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/suppliers?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération des fournisseurs :', error);
    throw error;
  }
};

// Récupérer un fournisseur par ID
export const getSupplierById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/suppliers/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération du fournisseur ${id} :`, error);
    throw error;
  }
};