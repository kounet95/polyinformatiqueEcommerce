const BASE_URL = 'http://localhost:8888/ecpolycommand';

// OrderLine API - Command

// Créer une ligne de commande
export const createOrderLine = async (orderLineData) => {
  try {
    const response = await fetch(`${BASE_URL}/order-line/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(orderLineData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création de la ligne de commande:', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un agrégat (ligne de commande)
// aggregateId = orderLineId
export const getOrderLineEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/order-line/command/events/${aggregateId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération des événements pour l'agrégat ${aggregateId}:`, error);
    throw error;
  }
};