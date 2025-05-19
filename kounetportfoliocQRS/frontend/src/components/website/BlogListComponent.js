import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllArticles } from '../../api/blog/query';
import '../../assets/css/blog.css';

const BlogListComponent = () => {
    const [articles, setArticles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchArticles = async () => {
            try {
                setLoading(true);
                const data = await getAllArticles();
                setArticles(data);
               console.log(data);

                setLoading(false);
            } catch (error) {
                console.error('Error fetching articles:', error);
                setError('Failed to load articles. Please try again later.');
                setLoading(false);
            }
        };
        fetchArticles();
    }, []);

    if (loading) return <div className="loading">Loading articles...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <main className="main">
            {/* Page Title */}
            <div className="page-title" data-aos="fade">
                <div className="heading">
                    <div className="container">
                        <div className="row d-flex justify-content-center text-center">
                            <div className="col-lg-8">
                                <h1>Blog</h1>
                                <p className="mb-0">Discover our latest articles, news, and insights about technology, programming, and more.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><Link to="/">Home</Link></li>
                            <li className="current">Blog</li>
                        </ol>
                    </div>
                </nav>
            </div>

            {/* Blog Posts Section */}
            <section id="blog-posts" className="blog-posts section">
                <div className="container">
                    <div className="row gy-4">
                        {articles.length > 0 ? (
                            articles.map((article) => (
                                <div className="col-lg-4" key={article.id}>
                                    <article>
                                        <div className="post-img">
                                            <img 
                                                src={article.imageUrl || "/assets/img/blog/blog-placeholder.jpg"} 
                                                alt={article.title} 
                                                className="img-fluid"
                                            />
                                        </div>

                                        <p className="post-category">{article.category || "Uncategorized"}</p>

                                        <h2 className="title">
                                            <Link to={`/blog/${article.id}`}>{article.title}</Link>
                                        </h2>

                                        <div className="d-flex align-items-center">
                                            <img 
                                                src={article.author?.avatarUrl || "assets/img/blog/blog-author.jpg"} 
                                                alt={article.author?.name || "Author"} 
                                                className="img-fluid post-author-img flex-shrink-0"
                                            />
                                            <div className="post-meta">
                                                <p className="post-author">{article.author?.name || "Anonymous"}</p>
                                                <p className="post-date">
                                                    <time dateTime={article.createdAt}>
                                                        {new Date(article.createdAt).toLocaleDateString('en-US', {
                                                            year: 'numeric',
                                                            month: 'short',
                                                            day: 'numeric'
                                                        })}
                                                    </time>
                                                </p>
                                            </div>
                                        </div>
                                    </article>
                                </div>
                            ))
                        ) : (
                            <div className="col-12 text-center">
                                <p>No articles found. Check back later for new content!</p>
                            </div>
                        )}
                    </div>
                </div>
            </section>

            {/* Blog Pagination Section */}
            {articles.length > 0 && (
                <section id="blog-pagination" className="blog-pagination section">
                    <div className="container">
                        <div className="d-flex justify-content-center">
                            <ul>
                                <li><a href="#"><i className="bi bi-chevron-left"></i></a></li>
                                <li><a href="#" className="active">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#"><i className="bi bi-chevron-right"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </section>
            )}
        </main>
    );
};

export default BlogListComponent;
