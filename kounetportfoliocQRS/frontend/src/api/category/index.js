const BASE_URL = 'http://localhost:8888/queryblog';

// Category API
export const getAllCategories = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/categories`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching categories:', error);
    throw error;
  }
};

export const getPagedCategories = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/categories/paged?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching paged categories:`, error);
    throw error;
  }
};

export const getCategoryById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/categories/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching category ${id}:`, error);
    throw error;
  }
};