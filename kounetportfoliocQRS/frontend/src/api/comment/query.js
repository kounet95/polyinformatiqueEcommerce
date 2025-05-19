const BASE_URL = 'http://localhost:8888/queryblog';


export const getAllComments = async () => {
  try {
    const response = await fetch(`${BASE_URL}/comments`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching comments:', error);
    throw error;
  }
};

export const getCommentById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/comments/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching comment ${id}:`, error);
    throw error;
  }
};