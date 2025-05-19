const BASE_URL = 'http://localhost:8888';

// Media API - Command
export const createMedia = async (mediaData) => {
  try {
    const response = await fetch(`${BASE_URL}/media/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(mediaData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating media:', error);
    throw error;
  }
};

export const getMediaEvents = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/media/command/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching media events ${id}:`, error);
    throw error;
  }
};