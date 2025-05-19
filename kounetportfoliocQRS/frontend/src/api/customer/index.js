const BASE_URL = 'http://localhost:8888/ecpolyinformatique';

// Customer API
export const getAllCustomers = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/customers`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching customers:', error);
    throw error;
  }
};

export const getPagedCustomers = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/customers/paged?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching paged customers:`, error);
    throw error;
  }
};

export const getCustomerById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/customers/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching customer ${id}:`, error);
    throw error;
  }
};