const BASE_URL = 'http://localhost:8888/queryblog';

// Blog API - Query
export const getAllArticles = async () => {
  try {
    const response = await fetch(`${BASE_URL}/query/article/articles`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching articles:', error);
    throw error;
  }
};

export const getArticleById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/query/article/articles/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching article ${id}:`, error);
    throw error;
  }
};

export const getAllComments = async () => {
  try {
    const response = await fetch(`${BASE_URL}/comments`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching comments:', error);
    throw error;
  }
};

export const getCommentById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/comments/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching comment ${id}:`, error);
    throw error;
  }
};

export const getAllEvents = async () => {
  try {
    const response = await fetch(`${BASE_URL}/events`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching events:', error);
    throw error;
  }
};

export const getEventById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching event ${id}:`, error);
    throw error;
  }
};

export const getAllNews = async () => {
  try {
    const response = await fetch(`${BASE_URL}/news`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching news:', error);
    throw error;
  }
};

export const getNewsById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/news/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching news ${id}:`, error);
    throw error;
  }
};

export const getAllTags = async () => {
  try {
    const response = await fetch(`${BASE_URL}/tags`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching tags:', error);
    throw error;
  }
};

export const getTagById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/tags/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching tag ${id}:`, error);
    throw error;
  }
};

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

export const getAllAuthors = async () => {
  try {
    const response = await fetch(`${BASE_URL}/utilisateurs`);
    return await response.json();
  } catch (error) {
    console.error('Error fetching authors:', error);
    throw error;
  }
};

export const getAuthorById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/utilisateurs/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching author ${id}:`, error);
    throw error;
  }
};