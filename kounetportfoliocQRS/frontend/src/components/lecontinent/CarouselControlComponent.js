import React from 'react'

const CarouselControlComponent = ({className, dataSlide}) => {
  return (
    <a className={className} href="#header-carousel" data-slide={dataSlide}>
        <div className="btn btn-dark" style={{width: "45px", height: "45px"}}>
            <span className="carousel-control-prev-icon mb-n2"></span>
        </div>
    </a>
  )
}

export default CarouselControlComponent
