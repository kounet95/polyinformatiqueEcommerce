import React from 'react';
import HeaderComponent from '../../components/website/HeaderComponent';

const HomePage = () => {
    return (
        <>
       
        <main className="main">

            
            <section id="hero" className="hero section dark-background">
        
              <img src="/assets/img/hero-bg.jpg" alt="" data-aos="fade-in"/>
        
              <div className="container">
                <div className="row">
                  <div className="col-lg-10">
                    <h2 data-aos="fade-up" data-aos-delay="100">Welcome to Our Website</h2>
                    <p data-aos="fade-up" data-aos-delay="200">We are team of talented designers making websites with Bootstrap</p>
                  </div>
                  <div className="col-lg-5" data-aos="fade-up" data-aos-delay="300">
                    <form action="forms/newsletter.php" method="post" className="php-email-form">
                      <div className="sign-up-form"><input type="email" name="email"/><input type="submit" value="Subscribe"/></div>
                      <div className="loading">Loading</div>
                      <div className="error-message"></div>
                      <div className="sent-message">Your subscription request has been sent. Thank you!</div>
                    </form>
                  </div>
                </div>
              </div>
        
            </section>
        
           
            <section id="clients" className="clients section">
        
              <div className="container" data-aos="fade-up">
        
                <div className="row gy-4">
        
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="/assets/img/clients/client-1.png" className="img-fluid" alt=""/>
                  </div>
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="/assets/img/clients/client-2.png" className="img-fluid" alt=""/>
                  </div>
        
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="/assets/img/clients/client-3.png" className="img-fluid" alt=""/>
                  </div>
        
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="assets/img/clients/client-4.png" className="img-fluid" alt=""/>
                  </div>
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="assets/img/clients/client-5.png" className="img-fluid" alt=""/>
                  </div>
        
                  <div className="col-xl-2 col-md-3 col-6 client-logo">
                    <img src="assets/img/clients/client-6.png" className="img-fluid" alt=""/>
                  </div>
        
                </div>
        
              </div>
        
            </section>
        </main>
        </>
    );
};

export default HomePage;
