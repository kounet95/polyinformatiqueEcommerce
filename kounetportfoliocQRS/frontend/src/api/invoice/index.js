const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Invoice API
export const getAllInvoices = async () => {
  try {
    const response = await fetch(`${BASE_URL}/api/invoices`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching invoices:', error);
    throw error;
  }
};

export const getPagedInvoices = async (page = 0, size = 10) => {
  try {
    const response = await fetch(`${BASE_URL}/api/invoices/paged?page=${page}&size=${size}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching paged invoices:`, error);
    throw error;
  }
};

export const getInvoiceById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/api/invoices/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching invoice ${id}:`, error);
    throw error;
  }
};