const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer un groupe social
export const createSocialGroup = async (socialGroupData) => {
  try {
    const response = await fetch(`${BASE_URL}/social-group/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(socialGroupData),
    });
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la création du groupe social :', error);
    throw error;
  }
};

// Récupérer le flux d'événements d'un groupe social (aggregateId = socialGroupId)
export const getSocialGroupEvents = async (aggregateId) => {
  try {
    const response = await fetch(`${BASE_URL}/social-group/command/events/${aggregateId}`, {
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