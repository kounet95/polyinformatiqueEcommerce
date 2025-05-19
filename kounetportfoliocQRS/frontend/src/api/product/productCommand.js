const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer un produit (avec ou sans image)
export const createProduct = async (productData, mediaFile) => {
  try {
    const formData = new FormData();
    // Le backend attend le DTO sous la clé "product" en JSON
    formData.append('product', new Blob([JSON.stringify(productData)], { type: 'application/json' }));
    if (mediaFile) {
      formData.append('media', mediaFile);
    }

    const response = await fetch(`${BASE_URL}/product/command/create`, {
      method: 'POST',
      body: formData,
      // Attention: ne PAS mettre Content-Type, il sera auto-détecté (multipart)
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création du produit:', error);
    throw error;
  }
};

// Upload d'une image seule (optionnel, si utilisé séparément)
export const uploadProductImage = async (file) => {
  try {
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch(`${BASE_URL}/product/command/upload-image`, {
      method: 'POST',
      body: formData,
      // Ne pas mettre Content-Type ici non plus
    });
    return await response.text(); // Renvoie juste l'URL de l'image
  } catch (error) {
    console.error('Erreur lors de l\'upload de l\'image:', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un produit (aggregateId = productId)
export const getProductEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/product/command/events/${aggregateId}`, {
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