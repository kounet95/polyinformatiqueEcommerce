const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Récupérer tous les groupes sociaux
export const getAllSocialGroups = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/socialgroups`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération de tous les groupes sociaux :', error);
    throw error;
  }
};

// Récupérer un groupe social par ID
export const getSocialGroupById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/socialgroups/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération du groupe social ${id} :`, error);
    throw error;
  }
};