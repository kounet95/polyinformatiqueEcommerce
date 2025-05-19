// reducers/articlesReducer.js
import {CREATE_ARTICLE, SET_ERROR, SET_LOADING} from '../actions/articleActions';
import {CREATE_DOMAIN, FETCH_DOMAINS} from "../actions/domainActions";

const initialState = {
    domains: [],
    domain:null,
    loading: false,
    error: null
};

const domainReducer = (state = initialState, action) => {
    switch (action.type) {

        case FETCH_DOMAINS:
            return { ...state, domains: action.payload };
        case CREATE_DOMAIN:
            return { ...state, domains: [...state.domains, action.payload] };
        case SET_LOADING:
            return { ...state, loading: action.payload };
        case SET_ERROR:
            return { ...state, error: action.payload };
        default:
            return state;
    }
};

export default domainReducer;
