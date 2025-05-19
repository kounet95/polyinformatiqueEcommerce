const BASE_URL = 'http://localhost:8888/commandeblog';

// Author API - Command
export const createAuthor = async (authorData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/author/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(authorData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating author:', error);
    throw error;
  }
};

export const getAuthorEvents = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/author/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching author events ${id}:`, error);
    throw error;
  }
};