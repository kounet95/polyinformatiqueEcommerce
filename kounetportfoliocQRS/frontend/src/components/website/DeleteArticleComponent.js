import React from 'react';
import axios from 'axios';
import { API_URL_DELETE_ARTICLE } from '../../api';

const DeleteArticleComponent = ({ articleId, onDelete }) => {
    const handleDelete = async () => {
        try {
            await axios.delete(API_URL_DELETE_ARTICLE(articleId));
            alert('Article deleted successfully!');
            if (onDelete) onDelete(articleId);
        } catch (error) {
            console.error('Error deleting article:', error);
        }
    };

    return <button onClick={handleDelete}>Delete Article</button>;
};

export default DeleteArticleComponent;