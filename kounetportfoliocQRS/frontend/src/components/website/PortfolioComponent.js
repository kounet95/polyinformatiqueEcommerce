import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const PortfolioComponent = () => {
  const [filter, setFilter] = useState('*');
  
  useEffect(() => {
    // This would typically be handled by a library like Isotope in a real implementation
    // For this example, we're just setting up the state
  }, [filter]);

  const handleFilterClick = (newFilter) => {
    setFilter(newFilter);
  };

  return (
    <section id="portfolio" className="portfolio section">
      <div className="container">
        <div className="section-title" data-aos="fade-up">
          <h2>Portfolio</h2>
          <p>Explore our diverse portfolio showcasing our best work across various domains.</p>
        </div>

        <div className="portfolio-isotope" data-aos="fade-up" data-aos-delay="100">
          <div className="portfolio-filters">
            <ul>
              <li 
                className={filter === '*' ? 'filter-active' : ''} 
                onClick={() => handleFilterClick('*')}
              >
                All
              </li>
              <li 
                className={filter === 'filter-app' ? 'filter-active' : ''} 
                onClick={() => handleFilterClick('filter-app')}
              >
                App
              </li>
              <li 
                className={filter === 'filter-product' ? 'filter-active' : ''} 
                onClick={() => handleFilterClick('filter-product')}
              >
                Product
              </li>
              <li 
                className={filter === 'filter-branding' ? 'filter-active' : ''} 
                onClick={() => handleFilterClick('filter-branding')}
              >
                Branding
              </li>
              <li 
                className={filter === 'filter-books' ? 'filter-active' : ''} 
                onClick={() => handleFilterClick('filter-books')}
              >
                Books
              </li>
            </ul>
          </div>

          <div className="row gy-4 portfolio-container">
            <div className="col-lg-4 col-md-6 portfolio-item filter-app">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/app-1.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>App 1</h4>
                  <p>App</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/app-1.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="App 1"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-4 col-md-6 portfolio-item filter-product">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/product-1.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>Product 1</h4>
                  <p>Product</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/product-1.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="Product 1"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-4 col-md-6 portfolio-item filter-branding">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/branding-1.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>Branding 1</h4>
                  <p>Branding</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/branding-1.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="Branding 1"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-4 col-md-6 portfolio-item filter-books">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/books-1.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>Books 1</h4>
                  <p>Books</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/books-1.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="Books 1"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-4 col-md-6 portfolio-item filter-app">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/app-2.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>App 2</h4>
                  <p>App</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/app-2.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="App 2"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-lg-4 col-md-6 portfolio-item filter-product">
              <div className="portfolio-wrap">
                <img src="/Append/assets/img/portfolio/product-2.jpg" className="img-fluid" alt="" />
                <div className="portfolio-info">
                  <h4>Product 2</h4>
                  <p>Product</p>
                  <div className="portfolio-links">
                    <a href="/Append/assets/img/portfolio/product-2.jpg" data-gallery="portfolioGallery" className="portfolio-lightbox" title="Product 2"><i className="bi bi-plus"></i></a>
                    <Link to="/portfolio-details" title="More Details"><i className="bi bi-link"></i></Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default PortfolioComponent;