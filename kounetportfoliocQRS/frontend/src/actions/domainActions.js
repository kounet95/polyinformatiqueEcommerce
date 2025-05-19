
// Action Types
import axios from "axios";
import {SET_ERROR, SET_LOADING, UPDATE_ARTICLE} from "./articleActions";
const API_URL_DOMAINS = 'http://localhost:8081/domains/command';
const API_URL_QUERY_DOMAIN = 'http://localhost:8082/domains';

export const FETCH_DOMAINS = 'FETCH_DOMAINS';
export const CREATE_DOMAIN = 'CREATE_DOMAIN';
export const UPDATE_DOMAIN = 'UPDATE_DOMAIN';




// Actions domain
export const fetchDomains = () => async (dispatch) => {
    try {
        dispatch({ type: SET_LOADING, payload: true });
        const response = await axios.get(`${API_URL_QUERY_DOMAIN}`);
        dispatch({ type: FETCH_DOMAINS, payload: response.data });
    } catch (error) {
        dispatch({ type: SET_ERROR, payload: error.message });
        console.log(error.message);
    } finally {
        dispatch({ type: SET_LOADING, payload: false });
    }
};

export const createDomain = (domainData) => async (dispatch) => {
    try {
        const response = await axios.post(`${API_URL_DOMAINS}/create`, domainData);
        console.log(response.data);

        dispatch({ type: CREATE_DOMAIN, payload: response.data });
    } catch (error) {
        console.log(error.message);
        dispatch({ type: SET_ERROR, payload: error.message });
    }
};

export const updateDomain = (id, data) => async (dispatch) => {
    try {
        const response = await axios.put(`${API_URL_DOMAINS}/${id}`, data);
        dispatch({ type: UPDATE_DOMAIN, payload: response.data });
    } catch (error) {
        dispatch({ type: SET_ERROR, payload: error.message });
    }
};
