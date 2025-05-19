import React from 'react';
import { Link } from 'react-router-dom';

const FooterComponent = () => {
  return (
    <footer className="footer bg-dark text-light pt-5 pb-3 mt-5">
      <div className="container">
        <div className="row gy-4">
          <div className="col-lg-4 col-md-6 mb-4">
            <Link to="/" className="d-flex align-items-center mb-3 text-decoration-none">
              <span className="fs-3 fw-bold text-primary">AFRICANITE</span>
            </Link>
            <p className="small">
              Bienvenue sur AFRICANITE, votre plateforme pour explorer, apprendre et partager autour de la culture africaine.
            </p>
            <div className="d-flex gap-3 mt-3">
              <a href="https://twitter.com/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="Twitter">
                <i className="bi bi-twitter-x"></i>
              </a>
              <a href="https://facebook.com/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="Facebook">
                <i className="bi bi-facebook"></i>
              </a>
              <a href="https://instagram.com/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="Instagram">
                <i className="bi bi-instagram"></i>
              </a>
              <a href="https://linkedin.com/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="LinkedIn">
                <i className="bi bi-linkedin-x"></i>
              </a>
              <a href="https://youtube.com/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="YouTube">
                <i className="bi bi-youtube"></i>
              </a>
              <a href="https://wa.me/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="WhatsApp">
                <i className="bi bi-whatsapp"></i>
              </a>
              <a href="https://t.me/" target="_blank" rel="noopener noreferrer" className="text-light fs-5" aria-label="Telegram">
                <i className="bi bi-telegram"></i>
              </a>
            </div>
          </div>

          <div className="col-lg-2 col-md-6 mb-4">
            <h5 className="text-uppercase mb-3">Liens utiles</h5>
            <ul className="list-unstyled">
              <li><Link to="/" className="text-light text-decoration-none">Accueil</Link></li>
              <li><Link to="/#about" className="text-light text-decoration-none">À propos</Link></li>
              <li><Link to="/service" className="text-light text-decoration-none">Services</Link></li>
              <li><Link to="/" className="text-light text-decoration-none">Conditions</Link></li>
              <li><Link to="/" className="text-light text-decoration-none">Confidentialité</Link></li>
            </ul>
          </div>

          <div className="col-lg-2 col-md-6 mb-4">
            <h5 className="text-uppercase mb-3">Nos services</h5>
            <ul className="list-unstyled">
              <li><Link to="/service" className="text-light text-decoration-none">Web Design</Link></li>
              <li><Link to="/service" className="text-light text-decoration-none">Développement</Link></li>
              <li><Link to="/service" className="text-light text-decoration-none">Gestion Produit</Link></li>
              <li><Link to="/service" className="text-light text-decoration-none">Marketing</Link></li>
              <li><Link to="/service" className="text-light text-decoration-none">Graphisme</Link></li>
            </ul>
          </div>

          <div className="col-lg-4 col-md-6 mb-4">
            <h5 className="text-uppercase mb-3">Contact</h5>
            <ul className="list-unstyled small">
              <li><i className="bi bi-geo-alt-fill me-2"></i> 50 lisgar, SUDBURY, Ontario, P3E 3L8</li>
              <li><i className="bi bi-telephone-fill me-2"></i>5149990594</li>
              <li><i className="bi bi-envelope-fill me-2"></i>polyinformatique@gmail.com</li>
            </ul>
          </div>
        </div>
        <hr className="border-secondary my-4" />
        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center">
          <p className="mb-2 mb-md-0 small">
            © {new Date().getFullYear()} <span className="fw-bold text-primary">AFRICANITE</span> — Tous droits réservés.
          </p>
          <div className="small">
            Design inspiré par <a href="https://bootstrapmade.com/" className="text-primary text-decoration-none" target="_blank" rel="noopener noreferrer">POLYINFORMATIQUE</a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default FooterComponent;