const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer une taille de produit
export const createProductSize = async (productSizeData) => {
  try {
    const response = await fetch(`${BASE_URL}/product-size/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(productSizeData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création de la taille de produit :', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'une taille de produit (aggregateId = productSizeId)
export const getProductSizeEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/product-size/command/events/${aggregateId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération des événements pour l'agrégat ${aggregateId} :`, error);
    throw error;
  }
};