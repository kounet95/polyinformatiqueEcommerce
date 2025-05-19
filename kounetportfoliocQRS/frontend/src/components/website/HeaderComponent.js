import React from 'react'

const HeaderComponent = ({title, content=''}) => {
  return (
    <div className="page-title">
      <div className="heading">
        <div className="container">
              <div className="row d-flex justify-content-center text-center">
                  <div className="col-lg-8">
                    <h1> {title}</h1>
                    <p className="mb-0">{content}</p>
                  </div>    
              </div>
            </div>
        </div>
    </div>
  )
}

export default HeaderComponent
