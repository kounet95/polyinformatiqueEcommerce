import React from 'react';
import { Link } from 'react-router-dom';

const ServicesDetailsComponent = () => {
  return (
    <section id="service-details" className="service-details section">
      <div className="container">
        <div className="row gy-5">
          <div className="col-lg-4" data-aos="fade-up" data-aos-delay="100">
            <div className="service-box">
              <h4>Services List</h4>
              <div className="services-list">
                <a href="#" className="active"><i className="bi bi-arrow-right-circle"></i><span>Web Design</span></a>
                <a href="#"><i className="bi bi-arrow-right-circle"></i><span>Web Development</span></a>
                <a href="#"><i className="bi bi-arrow-right-circle"></i><span>Product Management</span></a>
                <a href="#"><i className="bi bi-arrow-right-circle"></i><span>Graphic Design</span></a>
                <a href="#"><i className="bi bi-arrow-right-circle"></i><span>Marketing</span></a>
              </div>
            </div>

            <div className="service-box">
              <h4>Download Catalog</h4>
              <div className="download-catalog">
                <a href="#"><i className="bi bi-filetype-pdf"></i><span>Catalog PDF</span></a>
                <a href="#"><i className="bi bi-file-earmark-word"></i><span>Catalog DOC</span></a>
              </div>
            </div>

            <div className="help-box d-flex flex-column justify-content-center align-items-center">
              <i className="bi bi-headset help-icon"></i>
              <h4>Have a Question?</h4>
              <p className="d-flex align-items-center mt-2 mb-0"><i className="bi bi-telephone me-2"></i> <span>+1 5589 55488 55</span></p>
              <p className="d-flex align-items-center mt-1 mb-0"><i className="bi bi-envelope me-2"></i> <a href="mailto:contact@example.com">contact@example.com</a></p>
            </div>
          </div>

          <div className="col-lg-8 ps-lg-5" data-aos="fade-up" data-aos-delay="200">
            <img src="/Append/assets/img/services.jpg" alt="" className="img-fluid services-img" />
            <h3>Temporibus et in vero dicta aut eius lidero plastis trand lined voluptas dolorem ut voluptas</h3>
            <p>
              Blanditiis voluptate odit ex error ea sed officiis deserunt. Cupiditate non consequatur et doloremque consequuntur. Accusantium labore reprehenderit error temporibus saepe perferendis fuga doloribus vero. Qui omnis quo sit. Dolorem architecto eum et quos deleniti officia qui.
            </p>
            <ul>
              <li><i className="bi bi-check-circle"></i> <span>Aut eum totam accusantium voluptatem.</span></li>
              <li><i className="bi bi-check-circle"></i> <span>Assumenda et porro nisi nihil nesciunt voluptatibus.</span></li>
              <li><i className="bi bi-check-circle"></i> <span>Ullamco laboris nisi ut aliquip ex ea</span></li>
            </ul>
            <p>
              Est reprehenderit voluptatem necessitatibus asperiores neque sed ea illo. Deleniti quam sequi optio iste veniam repellat odit. Aut pariatur itaque nesciunt fuga.
            </p>
            <p>
              Sunt rem odit accusantium omnis perspiciatis officia. Laboriosam aut consequuntur recusandae mollitia doloremque est architecto cupiditate ullam. Quia est ut occaecati fuga. Distinctio ex repellendus eveniet velit sint quia sapiente cumque. Et ipsa perferendis ut nihil. Laboriosam vel voluptates tenetur nostrum. Eaque iusto cupiditate et totam et quia dolorum in. Sunt molestiae ipsum at consequatur vero. Architecto ut pariatur autem ad non cumque nesciunt qui maxime. Sunt eum quia impedit dolore alias explicabo ea.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ServicesDetailsComponent;