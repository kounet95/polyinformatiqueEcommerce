const BASE_URL = 'http://localhost:8888/queryblog';

// Récupérer toutes les actualités (news)
export const getAllNews = async () => {
  try {
    const response = await fetch(`${BASE_URL}/news`);
    return await response.json();
  } catch (error) {
    console.error('Erreur lors de la récupération des actualités :', error);
    throw error;
  }
};

// Récupérer une actualité par son ID
export const getNewsById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/news/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération de l'actualité ${id} :`, error);
    throw error;
  }
};