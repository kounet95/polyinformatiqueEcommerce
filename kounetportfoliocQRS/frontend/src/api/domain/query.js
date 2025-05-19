const BASE_URL = 'http://localhost:8888/queryblog';

// Domain API - Query
export const getAllDomains = async () => {
  try {
    const response = await fetch(`${BASE_URL}/domains`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching domains:', error);
    throw error;
  }
};

export const getDomainById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/domains/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching domain ${id}:`, error);
    throw error;
  }
};