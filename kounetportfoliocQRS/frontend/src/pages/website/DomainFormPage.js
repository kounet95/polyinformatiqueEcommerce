// src/components/BlogFormPage.js
import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {createDomain} from '../../actions/domainActions';

import HeaderComponent from '../../components/website/HeaderComponent';


const DomainFormPage = () => {
    const dispatch = useDispatch();
    const [domainData , setDomainData] = useState({
        name: '', articles:[]
    });


    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(createDomain(domainData));
    };

    const clear = () => {

    }

    return (
        <>
            <div className="page-title" data-aos="fade">
                <HeaderComponent title = "Nouveau domaine"/>
                <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><a href="/frontend/public">Accueil</a></li>
                            <li><a href="/domain">Domaine</a></li>
                            <li className="current">Nouveau domaine</li>
                        </ol>
                    </div>
                </nav>
            </div>
            <div className="container">
                <div className="row">
                    <div className="col-lg-8">
                        <section id="blog-details" className="blog-details section">
                            <div className="container">
                                <article className="article">
                                    <form onSubmit={handleSubmit}  className="form-group">
                                        <div className="form-group">
                                            <label>Name</label>
                                            <input type="text"  className="form-control" value={domainData.name} onChange={(e) => setDomainData({...domainData,name:e.target.value})} />
                                        </div>

                                        <div className="form-group d-flex justify-content-evenly pt-4">

                                            <button type="reset" onClick={clear} className="btn btn-secondary">Clear</button>
                                            <button type="submit" className="btn btn-primary">Create Domain</button>

                                        </div>

                                    </form>
                                </article>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </>
    );
};

export default DomainFormPage;
