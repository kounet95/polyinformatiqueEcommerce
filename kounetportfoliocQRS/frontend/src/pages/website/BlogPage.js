import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchArticles } from '../../actions/articleActions';
import HeaderComponent from '../../components/website/HeaderComponent';
import PaginatorComponent from '../../components/website/PaginatorComponent';

const BlogPage = () => {
    const dispatch = useDispatch();
    const { articles, loading, error } = useSelector(state => state.articles);
    useEffect(() => {
        dispatch(fetchArticles());
    }, [dispatch]);
    return (
        <>
           
           <div className="page-title" data-aos="fade">
                <HeaderComponent title="Blog" />
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

                  
                {articles.map(article => (

                    <div  key={article.id} className="col-lg-4">
                        <article>

                            <div className="post-img">
                                <img src="assets/img/blog/blog-6.jpg" alt="" className="img-fluid"/>
                            </div>

                            <p className="post-category">{article.title}</p>

                            <h2 className="title">
                                <a href={`/blog/${article.id}`}>{article.content}</a>
                            </h2>

                            <div className="d-flex justify-content-evenly ">
                                    <img src="assets/img/blog/blog-1.jpg" alt="" className="img-fluid post-author-img flex-shrink-0"/>
                                    <div className="post-meta">
                                        <p className="post-author">{article.author}</p>
                                        <p className="post-date">
                                            <time dateTime="2022-01-01">{article.createdAt}</time>
                                        </p>
                                    </div>
                                    <a><span className='text-info '><i className="fa-solid fa-user-pen"></i></span></a>
                                    <a><span className='text-danger '><i className="fa-solid fa-trash"></i></span></a>
                                    <i className="fa-solid fa-comments">10</i>
                            </div>
                        </article>
                    </div>
                ))}
                </div>
            </div>

        </section>
        <PaginatorComponent/>
        </>
            );
};

export default BlogPage;
