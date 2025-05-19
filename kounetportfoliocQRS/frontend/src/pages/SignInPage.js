import React from 'react'
import HeaderComponent from '../components/website/HeaderComponent'
import SocialSectionComponent from '../components/website/SocialSectionComponent'

const SignInPage = () => {
  return (
        <div className="page-title" data-aos="fade">
            <HeaderComponent title="Log in" />
            <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><a href="/">Accueil</a></li>
                        <li className="current">Sign in</li>
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
                                <div className="mb-3 form-check d-flex justify-content-around">
                                    <div>
                                        <input type="checkbox" className="form-check-input" id="rememberMe"/>
                                        <label className="form-check-label" for="rememberMe">Remember me</label>
                                    </div>
                                    <button type="submit" className="btn btn-primary btn-sm" style={{ width: '150px' }}>Login</button>
                                </div>
                            </form>
                            <div className="text-center d-flex justify-content-around">
                                <a href="/forgotten" className="text-decoration-none">Forgot password?</a>
                                <a href="/signup" className="text-decoration-none">Sign up</a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
  )
}

export default SignInPage
