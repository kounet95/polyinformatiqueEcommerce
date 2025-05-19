import React from 'react'
import CarouselItemComponent from './CarouselItemComponent'
import CarouselControlComponent from './CarouselControlComponent'
import MainNavComponent from './MainNavComponent'
import CategoriesComponent from './category/CategoriesComponent'

const NavBarComponentWithCaroussel = () => {
  return (
    <div className="container-fluid mb-5">
        <div className="row border-top px-xl-5">
            <CategoriesComponent />
            <div className="col-lg-9">
                <nav className="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                    <a href="" className="text-decoration-none d-block d-lg-none">
                        <h1 className="m-0 display-5 font-weight-semi-bold"><span className="text-primary font-weight-bold border px-3 mr-1">E</span>Shopper</h1>
                    </a>
                    <button type="button" className="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <MainNavComponent />
                </nav>
                <div id="header-carousel" className="carousel slide" data-ride="carousel">
                    <div className="carousel-inner">
                        <CarouselItemComponent image="carousel-1.jpg" active={false}/>
                        <CarouselItemComponent image="carousel-2.jpg" active={true}/>

                    </div>
                    <CarouselControlComponent className="carousel-control-prev" dataSlide="prev" />
                    <CarouselControlComponent className="carousel-control-next" dataSlide="next" />

                    
                </div>
            </div>
        </div>
    </div>
  )
}

export default NavBarComponentWithCaroussel
