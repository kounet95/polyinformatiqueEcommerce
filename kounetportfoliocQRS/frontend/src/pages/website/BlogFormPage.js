// src/components/BlogFormPage.js
import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {createArticle, fetchArticles} from '../../actions/articleActions';

import HeaderComponent from '../../components/website/HeaderComponent';
import {fetchDomains} from "../../actions/domainActions";


const BlogFormPage = () => {
    const dispatch = useDispatch();
    const [articleData , setArticleData] = useState({
        authorId:'1', title: '', content: '', tags: '', urlMedia : '', domainId:'1', tagIds:[], commentIds:[]
    });
    const { domains, loading, error } = useSelector(state => state.domains);
    useEffect(() => {
        dispatch(fetchDomains());
    }, [dispatch]);


    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(createArticle(articleData));
    };
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        const reader = new FileReader();
      
        reader.onloadend = () => {
          setArticleData({ ...articleData, selectedFile: reader.result });
        };
      
        if (file) {
          reader.readAsDataURL(file);
        }
      };
      
    const clear = () => {

    }

    return (
        <>
        <div className="page-title" data-aos="fade">
            <HeaderComponent title = "Nouvel article"/>
            <nav className="breadcrumbs">
                <div className="container">
                    <ol>
                        <li><a href="/frontend/public">Accueil</a></li>
                    <li><a href="/blog">Blog</a></li>
                    <li className="current">Nouvel article</li>
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
                                        <label>Title</label>
                                        <input type="text"  className="form-control" value={articleData.title} onChange={(e) => setArticleData({...articleData,title:e.target.value})} />
                                    </div>
                                    <div className="form-group">
                                        <label>Content</label>
                                        <textarea  className="form-control" value={articleData.content} onChange={(e) => setArticleData({...articleData,content:e.target.value})} />
                                    </div>
                                   
                                    <div className="form-group">
                                        <label>Tags</label>
                                        <input  className="form-control" type="text" value={articleData.tags} onChange={(e) => setArticleData({...articleData, tags:e.target.value})} />
                                    </div>
                                    <div className="form-group">
                                        <label>Image</label>
                                        <input type="file" accept="image/*"  className="form-control"  onChange={handleFileChange} />
                                    </div>
                                    <div className="form-group d-flex justify-content-evenly pt-4">

                                        <button type="reset" onClick={clear} className="btn btn-secondary">Clear</button>
                                        <button type="submit" className="btn btn-primary">Create Article</button>

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

export default BlogFormPage;
