// reducers/articlesReducer.js
import { FETCH_ARTICLES, CREATE_ARTICLE, UPDATE_ARTICLE, DELETE_ARTICLE, SET_LOADING, SET_ERROR, FETCH_ARTICLE } from '../actions/articleActions';

const initialState = {
    articles: [],
    article: null, 
    loading: false,
    error: null
};

const articlesReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_ARTICLE:
            return { ...state, article: action.payload };
        case FETCH_ARTICLES:
            return { ...state, articles: action.payload };
        case CREATE_ARTICLE:
            return { ...state, articles: [...state.articles, action.payload] };
        case UPDATE_ARTICLE:
            return {
                ...state,
                articles: state.articles.map(article =>
                    article.id === action.payload.id ? action.payload : article
                )
            };
        case DELETE_ARTICLE:
            return {
                ...state,
                articles: state.articles.filter(article => article.id !== action.payload)
            };
        case SET_LOADING:
            return { ...state, loading: action.payload };
        case SET_ERROR:
            return { ...state, error: action.payload };
        default:
            return state;
    }
};

export default articlesReducer;
