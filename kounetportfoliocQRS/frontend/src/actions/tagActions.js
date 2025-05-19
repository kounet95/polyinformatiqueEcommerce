
// Action Types
import axios from "axios";
import {SET_ERROR, SET_LOADING} from "./articleActions";
const API_URL_TAGS = 'http://localhost:8081/tag/command';
const API_URL_QUERY_TAG = 'http://localhost:8082/tags';

export const FETCH_TAGS = 'FETCH_TAGS';
export const CREATE_TAG = 'CREATE_TAG';




// Actions domain
export const fetchTags = () => async (dispatch) => {
    try {
        dispatch({ type: SET_LOADING, payload: true });
        const response = await axios.get(`${API_URL_QUERY_TAG}`);
        dispatch({ type: FETCH_TAGS, payload: response.data });
    } catch (error) {
        dispatch({ type: SET_ERROR, payload: error.message });
        console.log(error.message);
    } finally {
        dispatch({ type: SET_LOADING, payload: false });
    }
};

export const createTag = (data) => async (dispatch) => {
    try {
        const response = await axios.post(`${API_URL_TAGS}/create`, data);

        dispatch({ type: CREATE_TAG, payload: response.data });
    } catch (error) {
        console.log(error.message);
        dispatch({ type: SET_ERROR, payload: error.message });
    }
};