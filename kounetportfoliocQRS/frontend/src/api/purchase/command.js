const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Envoyer une réception d'achat (Purchase)
export const receivePurchase = async (purchaseData) => {
  try {
    const response = await fetch(`${BASE_URL}/purchase/command/receive`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(purchaseData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la réception de l\'achat:', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un achat (aggregateId = purchaseId)
export const getPurchaseEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/purchase/command/events/${aggregateId}`, {
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