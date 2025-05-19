import React from 'react';

const CarouselItemComponent = ({ image, active }) => {
  return (
    <div className={`carousel-item ${active ? 'active' : ''}`} style={{ height: "410px" }}>
      <img className="img-fluid" src={`img/${image}`} alt="Image" />
      <div className="carousel-caption d-flex flex-column align-items-center justify-content-center">
        <div className="p-3" style={{ maxWidth: "700px" }}>
          <h4 className="text-light text-uppercase font-weight-medium mb-3">10% Off Your First Order</h4>
          <h3 className="display-4 text-white font-weight-semi-bold mb-4">Fashionable Dress</h3>
          <a href="#" className="btn btn-light py-2 px-3">Shop Now</a>
        </div>
      </div>
    </div>
  );
};

export default CarouselItemComponent;
