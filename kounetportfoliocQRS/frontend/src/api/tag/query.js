const BASE_URL = 'http://localhost:8888';

// Tag API - Query
export const getAllTags = async () => {
  try {
    const response = await fetch(`${BASE_URL}/tags`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching tags:', error);
    throw error;
  }
};

export const getTagById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/tags/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching tag ${id}:`, error);
    throw error;
  }
};