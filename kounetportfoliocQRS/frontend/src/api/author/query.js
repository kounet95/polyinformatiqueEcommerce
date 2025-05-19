const BASE_URL = 'http://localhost:8888/queryBlog';

// Author API - Query
export const getAllAuthors = async () => {
  try {
    const response = await fetch(`${BASE_URL}/utilisateurs`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching authors:', error);
    throw error;
  }
};

export const getAuthorById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/utilisateurs/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching author ${id}:`, error);
    throw error;
  }
};