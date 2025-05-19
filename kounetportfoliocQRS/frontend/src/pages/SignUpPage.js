import React from 'react'
import HeaderComponent from '../components/website/HeaderComponent'
import SocialSectionComponent from '../components/website/SocialSectionComponent'

const SignUpPage = () => {
  return (
    <div className="page-title" data-aos="fade">
    <HeaderComponent title="Register" />
    <nav className="breadcrumbs">
            <div className="container">
                <ol>
                    <li><a href="/">Accueil</a></li>
                <li className="current">Sign up</li>
                </ol>
            </div>
        </nav>
    <div className="row justify-content-center mt-5">
        <div className="col-md-6">
            <div className="card">
                <div className="card-body">
                    <SocialSectionComponent />
                    <p className="divider-text">
                        <span className="bg-light">OR</span>
                    </p>
                    <form className='w-50 mx-auto'>
                        <div className="mb-3">
                            <label for="email" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="email" required/>
                        </div>
                        <div className="mb-3">
                            <label for="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" required/>
                        </div>
                        <div className="mb-3">
                            <label for="confirmation_password" className="form-label">Password Confirmation</label>
                            <input type="password" className="form-control" id="confirmation_password" required/>
                        </div>
                       
                        <div className="d-grid d-flex justify-content-around">
                            <a href="/signin" className="text-decoration-none">Sign in</a>
                            <button type="submit" className="btn btn-primary">Submit</button>
                        </div>
                    </form>
                   
                </div>
            </div>
        </div>
    </div>
</div>
  )
}

export default SignUpPage
