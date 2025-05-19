import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const PortfolioDetailsComponent = () => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slides = [
    '/Append/assets/img/portfolio/app-1.jpg',
    '/Append/assets/img/portfolio/product-1.jpg',
    '/Append/assets/img/portfolio/branding-1.jpg',
    '/Append/assets/img/portfolio/books-1.jpg'
  ];

  useEffect(() => {
    // Auto-advance slides
    const interval = setInterval(() => {
      setCurrentSlide((prevSlide) => (prevSlide + 1) % slides.length);
    }, 5000);

    return () => clearInterval(interval);
  }, [slides.length]);

  const goToSlide = (index) => {
    setCurrentSlide(index);
  };

  const goToPrevSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide - 1 + slides.length) % slides.length);
  };

  const goToNextSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % slides.length);
  };

  return (
    <section id="portfolio-details" className="portfolio-details section">
      <div className="container" data-aos="fade-up">
        <div className="portfolio-details-slider position-relative">
          <div className="swiper-wrapper align-items-center">
            {slides.map((slide, index) => (
              <div 
                key={index} 
                className={`swiper-slide ${index === currentSlide ? 'active' : 'hidden'}`}
                style={{ display: index === currentSlide ? 'block' : 'none' }}
              >
                <img src={slide} alt="" />
              </div>
            ))}
          </div>
          <div className="swiper-button-prev" onClick={goToPrevSlide}></div>
          <div className="swiper-button-next" onClick={goToNextSlide}></div>
          <div className="swiper-pagination">
            {slides.map((_, index) => (
              <span 
                key={index} 
                className={`swiper-pagination-bullet ${index === currentSlide ? 'swiper-pagination-bullet-active' : ''}`}
                onClick={() => goToSlide(index)}
              ></span>
            ))}
          </div>
        </div>

        <div className="row justify-content-between gy-4 mt-4">
          <div className="col-lg-8" data-aos="fade-up">
            <div className="portfolio-description">
              <h2>This is an example of portfolio details</h2>
              <p>
                Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore quia quia. Exercitationem repudiandae officiis neque suscipit non officia eaque itaque enim. Voluptatem officia accusantium nesciunt est omnis tempora consectetur dignissimos. Sequi nulla at esse enim cum deserunt eius.
              </p>
              <p>
                Amet consequatur qui dolore veniam voluptatem voluptatem sit. Non aspernatur atque natus ut cum nam et. Praesentium error dolores rerum minus sequi quia veritatis eum. Eos et doloribus doloremque nesciunt molestiae laboriosam.
              </p>

              <div className="testimonial-item">
                <p>
                  <i className="bi bi-quote quote-icon-left"></i>
                  <span>Export tempor illum tamen malis malis eram quae irure esse labore quem cillum quid cillum eram malis quorum velit fore eram velit sunt aliqua noster fugiat irure amet legam anim culpa.</span>
                  <i className="bi bi-quote quote-icon-right"></i>
                </p>
                <div>
                  <img src="/Append/assets/img/testimonials/testimonials-2.jpg" className="testimonial-img" alt="" />
                  <h3>Sara Wilsson</h3>
                  <h4>Designer</h4>
                </div>
              </div>

              <p>
                Impedit ipsum quae et aliquid doloribus et voluptatem quasi. Perspiciatis occaecati earum et magnam animi. Quibusdam non qui ea vitae suscipit vitae sunt. Repudiandae incidunt cumque minus deserunt assumenda tempore. Delectus voluptas necessitatibus est.
              </p>

              <p>
                Sunt voluptatum sapiente facilis quo odio aut ipsum repellat debitis. Molestiae et autem libero. Explicabo et quod necessitatibus similique quis dolor eum. Numquam eaque praesentium rem et qui nesciunt.
              </p>
            </div>
          </div>

          <div className="col-lg-3" data-aos="fade-up" data-aos-delay="100">
            <div className="portfolio-info">
              <h3>Project information</h3>
              <ul>
                <li><strong>Category</strong> Web design</li>
                <li><strong>Client</strong> ASU Company</li>
                <li><strong>Project date</strong> 01 March, 2020</li>
                <li><strong>Project URL</strong> <a href="#">www.example.com</a></li>
                <li><a href="#" className="btn-visit align-self-start">Visit Website</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default PortfolioDetailsComponent;