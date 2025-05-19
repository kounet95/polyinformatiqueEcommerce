import React from 'react'

const LocatorComponent = ({mainTitle}) => {
  return (
    <div className="container-fluid bg-secondary mb-5">
        <div className="d-flex flex-column align-items-center justify-content-center" style={{minHeight: "100px"}}>
            <h1 className="font-weight-semi-bold text-uppercase mb-3">{mainTitle}</h1>
            <div className="d-inline-flex">
                <p className="m-0"><a href="/">Home</a></p>
                <p className="m-0 px-2">-</p>
                <p className="m-0">{mainTitle}</p>
            </div>
        </div>
    </div>
  )
}

export default LocatorComponent
