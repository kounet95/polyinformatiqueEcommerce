const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Stock API - Query
export const getAllStocks = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/stocks`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching stocks:', error);
    throw error;
  }
};

export const getPagedStocks = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/stocks/paged?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching paged stocks:`, error);
    throw error;
  }
};

export const getStockById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/stocks/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching stock ${id}:`, error);
    throw error;
  }
};