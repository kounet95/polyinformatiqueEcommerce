import React, { useState } from 'react';

const LoginRegisterForm = () => {
  const [activeTab, setActiveTab] = useState('login');

  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };

  return (
    <div className="container mt-5">
      <div className="card mx-auto" style={{ maxWidth: '500px' }}>
        <div className="card-header">
          <ul className="nav nav-tabs card-header-tabs justify-content-center">
            <li className="nav-item">
              <button
                className={`nav-link ${activeTab === 'login' ? 'active' : ''}`}
                onClick={() => handleTabChange('login')}
              >
                Connexion
              </button>
            </li>
            <li className="nav-item">
              <button
                className={`nav-link ${activeTab === 'register' ? 'active' : ''}`}
                onClick={() => handleTabChange('register')}
              >
                Inscription
              </button>
            </li>
          </ul>
        </div>
        <div className="card-body">
          {activeTab === 'login' && (
            <form>
              <div className="mb-3">
                <label htmlFor="loginEmail" className="form-label">Email</label>
                <input type="email" className="form-control" id="loginEmail" placeholder="votre@email.com" />
              </div>
              <div className="mb-3">
                <label htmlFor="loginPassword" className="form-label">Mot de passe</label>
                <input type="password" className="form-control" id="loginPassword" />
              </div>
              <button type="submit" className="btn btn-primary w-100">Se connecter</button>
            </form>
          )}

          {activeTab === 'register' && (
            <form>
              <div className="mb-3">
                <label htmlFor="registerName" className="form-label">Nom complet</label>
                <input type="text" className="form-control" id="registerName" />
              </div>
              <div className="mb-3">
                <label htmlFor="registerEmail" className="form-label">Email</label>
                <input type="email" className="form-control" id="registerEmail" />
              </div>
              <div className="mb-3">
                <label htmlFor="registerPassword" className="form-label">Mot de passe</label>
                <input type="password" className="form-control" id="registerPassword" />
              </div>
              <button type="submit" className="btn btn-primary w-100">Créer un compte</button>
            </form>
          )}
        </div>
        <div className="d-inline-flex d-flex justify-content-around">
                    <a className="text-dark px-2" href="">
                        <i className="fab fa-facebook-f"></i>
                    </a>
                    <a className="text-dark px-2" href="">
                        <i className="fab fa-twitter"></i>
                    </a>
                    <a className="text-dark px-2" href="">
                        <i className="fab fa-linkedin-in"></i>
                    </a>
                    <a className="text-dark px-2" href="">
                        <i className="fab fa-instagram"></i>
                    </a>
                    <a className="text-dark pl-2" href="">
                        <i className="fab fa-google"></i>
                    </a>
                </div>
      </div>
    </div>
  );
};

export default LoginRegisterForm;
