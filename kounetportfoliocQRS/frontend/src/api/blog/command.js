const BASE_URL = 'http://localhost:8888/commandeblog';

// Blog API - Command
export const createArticle = async (articleData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(articleData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating article:', error);
    throw error;
  }
};

export const createNews = async (newsData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/create-news`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newsData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating news:', error);
    throw error;
  }
};

export const createEvent = async (eventData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/create-event`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(eventData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating event:', error);
    throw error;
  }
};

export const updateArticle = async (id, articleData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/update-article/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(articleData),
    });
    return await response.json();
  } catch (error) {
    console.error(`Error updating article ${id}:`, error);
    throw error;
  }
};

export const updateNews = async (id, newsData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/update-news/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newsData),
    });
    return await response.json();
  } catch (error) {
    console.error(`Error updating news ${id}:`, error);
    throw error;
  }
};

export const updateEvent = async (id, eventData) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/update-event/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(eventData),
    });
    return await response.json();
  } catch (error) {
    console.error(`Error updating event ${id}:`, error);
    throw error;
  }
};

export const deleteArticle = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/delete-article/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Error deleting article ${id}:`, error);
    throw error;
  }
};

export const deleteNews = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/delete-news/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Error deleting news ${id}:`, error);
    throw error;
  }
};

export const deleteEvent = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/blog/command/delete-event/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Error deleting event ${id}:`, error);
    throw error;
  }
};