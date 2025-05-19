// store.js
import { createStore, combineReducers, applyMiddleware } from 'redux';
import {thunk} from 'redux-thunk';  // pour gérer les actions asynchrones (API calls)

// Importer tes réducteurs
import articlesReducer from './reducers/articleReducer';
import domainReducer from "./reducers/domainReducer";

const rootReducer = combineReducers({
  articles: articlesReducer,
  domains : domainReducer
});

const store = createStore(rootReducer, applyMiddleware(thunk));

export default store;
