const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer une sous-catégorie
export const createSubcategory = async (subcategoryData) => {
  try {
    const response = await fetch(`${BASE_URL}/subcategory/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(subcategoryData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création de la sous-catégorie :', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'une sous-catégorie (aggregateId = subcategoryId)
export const getSubcategoryEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/subcategory/command/events/${aggregateId}`, {
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