import React from 'react';
import PortfolioComponent from '../../components/website/PortfolioComponent';

const PortfolioPage = () => {
  return (
    <main className="main">
      {/* Page Title */}
      <div className="page-title" data-aos="fade">
        <div className="heading">
          <div className="container">
            <div className="row d-flex justify-content-center text-center">
              <div className="col-lg-8">
                <h1>Portfolio</h1>
                <p className="mb-0">Odio et unde deleniti. Deserunt numquam exercitationem. Officiis quo odio sint voluptas consequatur ut a odio voluptatem. Sit dolorum debitis veritatis natus dolores. Quasi ratione sint. Sit quaerat ipsum dolorem.</p>
              </div>
            </div>
          </div>
        </div>
        <nav className="breadcrumbs">
          <div className="container">
            <ol>
              <li><a href="/frontend/public">Home</a></li>
              <li className="current">Portfolio</li>
            </ol>
          </div>
        </nav>
      </div>
      
      {/* Portfolio Component */}
      <PortfolioComponent />
    </main>
  );
};

export default PortfolioPage;