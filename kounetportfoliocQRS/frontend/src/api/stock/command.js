const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Ajouter du stock
export const addStock = async (stockData) => {
  try {
    const response = await fetch(`${BASE_URL}/stock/command/add`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(stockData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de l\'ajout du stock :', error);
    throw error;
  }
};

// Récupérer le flux d'événements pour un stock (aggregateId = stockId)
export const getStockEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/stock/command/events/${aggregateId}`, {
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