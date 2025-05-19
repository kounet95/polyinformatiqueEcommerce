const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Product API
export const getAllProducts = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/products`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching products:', error);
    throw error;
  }
};

export const getPagedProducts = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/products/paged?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching paged products:`, error);
    throw error;
  }
};

export const getProductById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/products/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching product ${id}:`, error);
    throw error;
  }
};