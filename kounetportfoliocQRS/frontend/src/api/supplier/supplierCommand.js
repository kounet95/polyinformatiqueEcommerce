const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer un fournisseur
export const createSupplier = async (supplierData) => {
  try {
    const response = await fetch(`${BASE_URL}/supplier/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(supplierData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création du fournisseur :', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un fournisseur (aggregateId = supplierId)
export const getSupplierEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/supplier/command/events/${aggregateId}`, {
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