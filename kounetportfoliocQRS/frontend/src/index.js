import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/website/index.css';
import './css/lecontinent/index.css';

import 'bootstrap/dist/css/bootstrap.css'
import App from './App';
import { Provider } from 'react-redux';
import reportWebVitals from './reportWebVitals';
import store from './store';
import { BrowserRouter } from 'react-router-dom';
import NavBarComponent from "./components/NavBarComponent";
import FooterComponent from "./components/website/FooterComponent";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <BrowserRouter>
          <Provider store={store}>
              <NavBarComponent/>
              <App />
            </Provider>
          <FooterComponent/>
      </BrowserRouter>

  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
