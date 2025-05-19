const BASE_URL = 'http://localhost:8888/commandeblog';

// Comment API - Command
export const createComment = async (commentData) => {
  try {
    const response = await fetch(`${BASE_URL}/comment/command/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(commentData),
    });
    return await response.json();
  } catch (error) {
    console.error('Error creating comment:', error);
    throw error;
  }
};

export const deleteComment = async (commentId, itemId) => {
  try {
    const response = await fetch(`${BASE_URL}/comment/command/delete/${commentId}?itemId=${itemId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return await response.json();
  } catch (error) {
    console.error(`Error deleting comment ${commentId}:`, error);
    throw error;
  }
};

export const getCommentEvents = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/comment/command/events/${id}`);
    return await response.json();
  } catch (error) {
    console.error(`Error fetching comment events ${id}:`, error);
    throw error;
  }
};