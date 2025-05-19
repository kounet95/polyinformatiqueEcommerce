const BASE_URL = 'http://localhost:8888/commandeblog';

// Domain API - Command
export const createDomain = async (domainData) => {
  try {
    const response = await fetch(`${BASE_URL}/category/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(domainData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating domain:', error);
    throw error;
  }
};

export const getDomainEvents = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/category/command/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching domain events ${id}:`, error);
    throw error;
  }
};