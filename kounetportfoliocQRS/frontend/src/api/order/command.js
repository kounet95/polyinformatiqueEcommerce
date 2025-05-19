const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Order API - Command

// Créer une commande
export const createOrder = async (orderData) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(orderData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création de la commande:', error);
    throw error;
  }
};

// Ajouter un produit à une commande
export const addProductToOrder = async (orderId, productData) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/${orderId}/add-product`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(productData),
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de l'ajout du produit à la commande ${orderId}:`, error);
    throw error;
  }
};

// Confirmer une commande
export const confirmOrder = async (orderId) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/${orderId}/confirm`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la confirmation de la commande ${orderId}:`, error);
    throw error;
  }
};

// Générer une facture pour une commande
export const generateInvoice = async (orderId, invoiceData) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/${orderId}/generate-invoice`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(invoiceData),
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la génération de la facture pour la commande ${orderId}:`, error);
    throw error;
  }
};

// Payer une facture
export const payInvoice = async (invoiceId) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/invoice/${invoiceId}/pay`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors du paiement de la facture ${invoiceId}:`, error);
    throw error;
  }
};

// Démarrer la livraison d'une commande
export const startShipping = async (orderId) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/${orderId}/start-shipping`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors du démarrage de la livraison pour la commande ${orderId}:`, error);
    throw error;
  }
};

// Marquer une commande comme livrée
export const deliverOrder = async (orderId) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/${orderId}/deliver`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la livraison de la commande ${orderId}:`, error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un agrégat (commande)
// aggregateId = orderId
export const getOrderEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/order/command/events/${aggregateId}`, {
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