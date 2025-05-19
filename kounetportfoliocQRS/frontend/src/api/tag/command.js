const BASE_URL = 'http://localhost:8888/ecpolycommand';

// Créer un tag
export const createTag = async (tagData) => {
  try {
    const response = await fetch(`${BASE_URL}/tag/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(tagData),
    });
    // Le backend retourne un status 200 OK sans body (ResponseEntity<Void>), donc inutile de faire response.json()
    if (!response.ok) throw new Error('Erreur lors de la création du tag');
    return true;
  } catch (error) {
    console.error('Erreur lors de la création du tag:', error);
    throw error;
  }
};

// Récupérer les événements d'un tag
export const getTagEvents = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/tag/command/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération des événements du tag ${id}:`, error);
    throw error;
  }
};

// Supprimer un tag
export const deleteTag = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/tag/command/delete/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Erreur lors de la suppression du tag');
    return true;
  } catch (error) {
    console.error('Erreur lors de la suppression du tag:', error);
    throw error;
  }
};