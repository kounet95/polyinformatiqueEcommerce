import React from 'react';
import { deleteArticle } from '../../api/blog/command';

const DeleteArticleComponent = ({ articleId, onDelete }) => {
  const handleDelete = async () => {
    try {
      await deleteArticle(articleId);
      alert('Article deleted successfully!');
      if (onDelete) onDelete(articleId);
    } catch (error) {
      console.error('Error deleting article:', error);
      alert('Failed to delete the article.');
    }
  };

  return <button onClick={handleDelete}>Delete Article</button>;
};

export default DeleteArticleComponent;
