const BASE_URL = 'http://localhost:8888';

// Media API - Query
// Note: There's no MediaQueryController in the backend, so these are placeholder functions
// that might need to be updated once the backend is implemented

export const getAllMedia = async () => {
  try {
    const response = await fetch(`${BASE_URL}/media`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching media:', error);
    throw error;
  }
};

export const getMediaById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/media/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching media ${id}:`, error);
    throw error;
  }
};