import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getArticleById } from '../../../src/api/blog/query';
import '../../assets/css/blog.css';

const BlogDetailComponent = () => {
    const { id } = useParams();
    const [article, setArticle] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [comments, setComments] = useState([]);

    useEffect(() => {
        const fetchArticle = async () => {
            try {
                setLoading(true);
                const data = await getArticleById(id);
                setArticle(data);
                // In a real app, you would fetch comments separately
                // For now, we'll use dummy data
                setComments([
                    { id: 1, author: 'Georgia Reader', date: '2022-01-01', content: 'Great article! Very informative and well-written.' },
                    { id: 2, author: 'Aron Alvarado', date: '2022-01-05', content: 'I learned a lot from this. Thanks for sharing your knowledge.' }
                ]);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching article:', error);
                setError('Failed to load article. Please try again later.');
                setLoading(false);
            }
        };
        fetchArticle();
    }, [id]);

    if (loading) return <div className="loading">Loading article...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!article) return <div className="error">Article not found</div>;

    return (
        <main className="main">
            {/* Page Title */}
            <div className="page-title" data-aos="fade">
                <div className="heading">
                    <div className="container">
                        <div className="row d-flex justify-content-center text-center">
                            <div className="col-lg-8">
                                <h1>Blog Details</h1>
                                <p className="mb-0">Dive deeper into our content and explore the details of our articles.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/blog">Blog</Link></li>
                            <li className="current">Article Details</li>
                        </ol>
                    </div>
                </nav>
            </div>

            <div className="container">
                <div className="row">
                    {/* Main Content */}
                    <div className="col-lg-8">
                        {/* Blog Details Section */}
                        <section id="blog-details" className="blog-details section">
                            <div className="container">
                                <article className="article">
                                    <div className="post-img">
                                        <img 
                                            src={article.imageUrl || "/assets/img/blog/blog-placeholder.jpg"} 
                                            alt={article.title} 
                                            className="img-fluid"
                                        />
                                    </div>

                                    <h2 className="title">{article.title}</h2>

                                    <div className="meta-top">
                                        <ul>
                                            <li className="d-flex align-items-center">
                                                <i className="bi bi-person"></i> 
                                                <a href="#">{article.author?.name || "Anonymous"}</a>
                                            </li>
                                            <li className="d-flex align-items-center">
                                                <i className="bi bi-clock"></i> 
                                                <a href="#">
                                                    <time dateTime={article.createdAt}>
                                                        {new Date(article.createdAt).toLocaleDateString('en-US', {
                                                            year: 'numeric',
                                                            month: 'short',
                                                            day: 'numeric'
                                                        })}
                                                    </time>
                                                </a>
                                            </li>
                                            <li className="d-flex align-items-center">
                                                <i className="bi bi-chat-dots"></i> 
                                                <a href="#">{comments.length} Comments</a>
                                            </li>
                                        </ul>
                                    </div>

                                    <div className="content">
                                        {/* Render the content - in a real app, you might use a rich text renderer */}
                                        <div dangerouslySetInnerHTML={{ __html: article.content }} />
                                    </div>

                                    <div className="meta-bottom">
                                        <i className="bi bi-folder"></i>
                                        <ul className="cats">
                                            <li><a href="#">{article.category || "Uncategorized"}</a></li>
                                        </ul>

                                        <i className="bi bi-tags"></i>
                                        <ul className="tags">
                                            {article.tags?.map((tag, index) => (
                                                <li key={index}><a href="#">{tag}</a></li>
                                            )) || <li><a href="#">General</a></li>}
                                        </ul>
                                    </div>
                                </article>
                            </div>
                        </section>

                        {/* Blog Author Section */}
                        <section id="blog-author" className="blog-author section">
                            <div className="container">
                                <div className="author-container d-flex align-items-center">
                                    <img 
                                        src={article.author?.avatarUrl || "/assets/img/blog/blog-author.jpg"} 
                                        className="rounded-circle flex-shrink-0" 
                                        alt={article.author?.name || "Author"}
                                    />
                                    <div>
                                        <h4>{article.author?.name || "Anonymous"}</h4>
                                        <div className="social-links">
                                            <a href="#"><i className="bi bi-twitter-x"></i></a>
                                            <a href="#"><i className="bi bi-facebook"></i></a>
                                            <a href="#"><i className="bi bi-instagram"></i></a>
                                        </div>
                                        <p>
                                            {article.author?.bio || "Author bio not available."}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </section>

                        {/* Blog Comments Section */}
                        <section id="blog-comments" className="blog-comments section">
                            <div className="container">
                                <h4 className="comments-count">{comments.length} Comments</h4>

                                {comments.map((comment) => (
                                    <div id={`comment-${comment.id}`} className="comment" key={comment.id}>
                                        <div className="d-flex">
                                            <div className="comment-img">
                                                <img src="/assets/img/blog/comments-1.jpg" alt="" />
                                            </div>
                                            <div>
                                                <h5>
                                                    <a href="">{comment.author}</a> 
                                                    <a href="#" className="reply"><i className="bi bi-reply-fill"></i> Reply</a>
                                                </h5>
                                                <time dateTime={comment.date}>
                                                    {new Date(comment.date).toLocaleDateString('en-US', {
                                                        year: 'numeric',
                                                        month: 'short',
                                                        day: 'numeric'
                                                    })}
                                                </time>
                                                <p>{comment.content}</p>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </section>

                        {/* Comment Form Section */}
                        <section id="comment-form" className="comment-form section">
                            <div className="container">
                                <form action="">
                                    <h4>Post Comment</h4>
                                    <p>Your email address will not be published. Required fields are marked * </p>
                                    <div className="row">
                                        <div className="col-md-6 form-group">
                                            <input name="name" type="text" className="form-control" placeholder="Your Name*" />
                                        </div>
                                        <div className="col-md-6 form-group">
                                            <input name="email" type="text" className="form-control" placeholder="Your Email*" />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col form-group">
                                            <input name="website" type="text" className="form-control" placeholder="Your Website" />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col form-group">
                                            <textarea name="comment" className="form-control" placeholder="Your Comment*"></textarea>
                                        </div>
                                    </div>

                                    <div className="text-center">
                                        <button type="submit" className="btn btn-primary">Post Comment</button>
                                    </div>
                                </form>
                            </div>
                        </section>
                    </div>

                    {/* Sidebar */}
                    <div className="col-lg-4 sidebar">
                        <div className="widgets-container">
                            {/* Search Widget */}
                            <div className="search-widget widget-item">
                                <h3 className="widget-title">Search</h3>
                                <form action="">
                                    <input type="text" />
                                    <button type="submit" title="Search"><i className="bi bi-search"></i></button>
                                </form>
                            </div>

                            {/* Categories Widget */}
                            <div className="categories-widget widget-item">
                                <h3 className="widget-title">Categories</h3>
                                <ul className="mt-3">
                                    <li><a href="#">Technology <span>(25)</span></a></li>
                                    <li><a href="#">Programming <span>(12)</span></a></li>
                                    <li><a href="#">Development <span>(5)</span></a></li>
                                    <li><a href="#">Design <span>(22)</span></a></li>
                                    <li><a href="#">Innovation <span>(8)</span></a></li>
                                </ul>
                            </div>

                            {/* Recent Posts Widget */}
                            <div className="recent-posts-widget widget-item">
                                <h3 className="widget-title">Recent Posts</h3>

                                {/* This would typically be populated from an API call */}
                                <div className="post-item">
                                    <img src="/assets/img/blog/blog-recent-1.jpg" alt="" className="flex-shrink-0" />
                                    <div>
                                        <h4><a href="#">Introduction to Web Development</a></h4>
                                        <time dateTime="2023-01-01">Jan 1, 2023</time>
                                    </div>
                                </div>

                                <div className="post-item">
                                    <img src="/assets/img/blog/blog-recent-2.jpg" alt="" className="flex-shrink-0" />
                                    <div>
                                        <h4><a href="#">Getting Started with React</a></h4>
                                        <time dateTime="2023-01-15">Jan 15, 2023</time>
                                    </div>
                                </div>

                                <div className="post-item">
                                    <img src="/assets/img/blog/blog-recent-3.jpg" alt="" className="flex-shrink-0" />
                                    <div>
                                        <h4><a href="#">Understanding Microservices Architecture</a></h4>
                                        <time dateTime="2023-02-01">Feb 1, 2023</time>
                                    </div>
                                </div>
                            </div>

                            {/* Tags Widget */}
                            <div className="tags-widget widget-item">
                                <h3 className="widget-title">Tags</h3>
                                <ul>
                                    <li><a href="#">React</a></li>
                                    <li><a href="#">JavaScript</a></li>
                                    <li><a href="#">Java</a></li>
                                    <li><a href="#">Spring</a></li>
                                    <li><a href="#">Microservices</a></li>
                                    <li><a href="#">CQRS</a></li>
                                    <li><a href="#">Frontend</a></li>
                                    <li><a href="#">Backend</a></li>
                                    <li><a href="#">Development</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
};

export default BlogDetailComponent;
