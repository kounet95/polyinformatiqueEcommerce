import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import HeaderComponent from '../../components/website/HeaderComponent';
import PaginatorComponent from '../../components/website/PaginatorComponent';
import {fetchArticles} from "../../actions/articleActions";
import {fetchDomains} from "../../actions/domainActions";

const DomainPage = () => {
    const dispatch = useDispatch();
    const { domains, loading, error } = useSelector(state => state.domains);
    useEffect(() => {
        dispatch(fetchDomains());
    }, [dispatch]);
    return (
        <>

            <div className="page-title" data-aos="fade">
                <HeaderComponent title="Domain" />
                <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><a href="/frontend/public">Accueil</a></li>
                            <li className="current">Blog</li>
                        </ol>
                    </div>
                </nav>
            </div>

            <section id="blog-posts" className="blog-posts section ">

                <div className="container ">
                    <div className="row gy-4 ">


                        {domains.map(article => (

                            <div  key={article.id} className="col-lg-4">



                                    <p className="post-category">{article.name}</p>


                            </div>
                        ))}
                    </div>
                </div>

            </section>
            <PaginatorComponent/>
        </>
    );
};

export default DomainPage;
