const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer un envoi (shipping)
export const createShipping = async (shippingData) => {
  try {
    const response = await fetch(`${BASE_URL}/shipping/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(shippingData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création de l\'envoi :', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un shipping (aggregateId = shippingId)
export const getShippingEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/shipping/command/events/${aggregateId}`, {
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