import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchArticle } from '../../actions/articleActions';
import { useParams } from 'react-router-dom';
import CommentFormComponent from '../../components/website/CommentFormComponent';
import HeaderComponent from '../../components/website/HeaderComponent';


const BlogDetailsPage = () => {
    const { id } = useParams();
    const dispatch = useDispatch();
    const { article, loading, error } = useSelector(state => state.articles);
    useEffect(() => {
            dispatch(fetchArticle(id));
    }, [dispatch, id]);

        if (loading) {
            return <p>Loading...</p>;
        }
    
        if (error) {
            return <p>Error: {error}</p>;
        }
        if (!article) {
            return <p>Article not found!</p>;
        }
    return (
        <>
        <div className="page-title" data-aos="fade">
            <HeaderComponent title="Parlons-en!"/>
            <nav className="breadcrumbs">
            <div className="container">
                <ol>
                  <li><a href="/frontend/public">Accueil</a></li>
                <li className="current">Article detaillee</li>
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
   
                   <div className="post-img">
                     <img src="/assets/img/blog/blog-1.jpg" alt="" className="img-fluid"/>
                   </div>
   
                   <h2 className="title">{article.title}</h2>
   
                   <div className="meta-top">
                     <ul>
                       <li className="d-flex align-items-center"><i className="fa-solid fa-person"></i> <a href="blog-details.html">John Doe</a></li>
                       <li className="d-flex align-items-center"><i className="fa-solid fa-clock"></i> <a href="blog-details.html"><time dateTime="2020-01-01">Jan 1, 2022</time></a></li>
                       <li className="d-flex align-items-center"><i className="fa-brands fa-rocketchat"></i> <a href="blog-details.html">12 Comments</a></li>
                     </ul>
                   </div>
   
                   <div className="content">
                    
                     <p>
                     {article.content}
                   </p>
                     <p>
                       Alias quia non aliquid. Eos et ea velit. Voluptatem maxime enim omnis ipsa voluptas incidunt. Nulla sit eaque mollitia nisi asperiores est veniam.
                     </p>
   
                   </div>
   
                   <div className="meta-bottom">
                   <i className="fa-solid fa-folder"></i>
                     <ul className="cats">
                       <li><a href="#">Business</a></li>
                     </ul>
   
                     <i className="fa-solid fa-tags"></i>
                     <ul className="tags">
                       <li><a href="#">Creative</a></li>
                       <li><a href="#">Tips</a></li>
                       <li><a href="#">Marketing</a></li>
                     </ul>
                   </div>
                 </article>
   
               </div>
             </section>
   
             
             <section id="blog-author" className="blog-author section">
   
               <div className="container">
                 <div className="author-container d-flex align-items-center">
                   <img src="/assets/img/blog/blog-author.jpg" className="rounded-circle flex-shrink-0" alt=""/>
                   <div>
                     <h4>Jane Smith</h4>
                     <div className="social-links">
                       <a href="https://x.com/#"><i className="fa-brands fa-x-twitter"></i></a>
                       <a href="https://facebook.com/#"><i className="fa-brands fa-facebook"></i></a>
                       <a href="https://instagram.com/#"><i className="fa-brands fa-instagram"></i></a>
                     </div>
                     <p>
                       Itaque quidem optio quia voluptatibus dolorem dolor. Modi eum sed possimus accusantium. Quas repellat voluptatem officia numquam sint aspernatur voluptas. Esse et accusantium ut unde voluptas.
                     </p>
                   </div>
                 </div>
               </div>
   
             </section>
   
            
             <section id="blog-comments" className="blog-comments section">
   
               <div className="container">
   
                 <h4 className="comments-count">8 Comments</h4>
   
                 <div id="comment-1" className="comment">
                   <div className="d-flex">
                     <div className="comment-img"><img src="/assets/img/blog/comments-1.jpg" alt=""/></div>
                     <div>
                       <h5><a href="">Georgia Reader</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                       <time dateTime="2020-01-01">01 Jan,2022</time>
                       <p>
                         Et rerum totam nisi. Molestiae vel quam dolorum vel voluptatem et et. Est ad aut sapiente quis molestiae est qui cum soluta.
                         Vero aut rerum vel. Rerum quos laboriosam placeat ex qui. Sint qui facilis et.
                       </p>
                     </div>
                   </div>
                 </div>
   
                 <div id="comment-2" className="comment">
                   <div className="d-flex">
                     <div className="comment-img"><img src="/assets/img/blog/comments-2.jpg" alt=""/></div>
                     <div>
                       <h5><a href="">Aron Alvarado</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                       <time dateTime="2020-01-01">01 Jan,2022</time>
                       <p>
                         Ipsam tempora sequi voluptatem quis sapiente non. Autem itaque eveniet saepe. Officiis illo ut beatae.
                       </p>
                     </div>
                   </div>
   
                   <div id="comment-reply-1" className="comment comment-reply">
                     <div className="d-flex">
                       <div className="comment-img"><img src="/assets/img/blog/comments-3.jpg" alt=""/></div>
                       <div>
                         <h5><a href="">Lynda Small</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                         <time dateTime="2020-01-01">01 Jan,2022</time>
                         <p>
                           Enim ipsa eum fugiat fuga repellat. Commodi quo quo dicta. Est ullam aspernatur ut vitae quia mollitia id non. Qui ad quas nostrum rerum sed necessitatibus aut est. Eum officiis sed repellat maxime vero nisi natus. Amet nesciunt nesciunt qui illum omnis est et dolor recusandae.
   
                           Recusandae sit ad aut impedit et. Ipsa labore dolor impedit et natus in porro aut. Magnam qui cum. Illo similique occaecati nihil modi eligendi. Pariatur distinctio labore omnis incidunt et illum. Expedita et dignissimos distinctio laborum minima fugiat.
   
                           Libero corporis qui. Nam illo odio beatae enim ducimus. Harum reiciendis error dolorum non autem quisquam vero rerum neque.
                         </p>
                       </div>
                     </div>
   
                     <div id="comment-reply-2" className="comment comment-reply">
                       <div className="d-flex">
                         <div className="comment-img"><img src="/assets/img/blog/comments-4.jpg" alt=""/></div>
                         <div>
                           <h5><a href="">Sianna Ramsay</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                           <time dateTime="2020-01-01">01 Jan,2022</time>
                           <p>
                             Et dignissimos impedit nulla et quo distinctio ex nemo. Omnis quia dolores cupiditate et. Ut unde qui eligendi sapiente omnis ullam. Placeat porro est commodi est officiis voluptas repellat quisquam possimus. Perferendis id consectetur necessitatibus.
                           </p>
                         </div>
                       </div>
   
                     </div>
   
                   </div>
   
                 </div>
   
                 <div id="comment-3" className="comment">
                   <div className="d-flex">
                     <div className="comment-img"><img src="/assets/img/blog/comments-5.jpg" alt=""/></div>
                     <div>
                       <h5><a href="">Nolan Davidson</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                       <time dateTime="2020-01-01">01 Jan,2022</time>
                       <p>
                         Distinctio nesciunt rerum reprehenderit sed. Iste omnis eius repellendus quia nihil ut accusantium tempore. Nesciunt expedita id dolor exercitationem aspernatur aut quam ut. Voluptatem est accusamus iste at.
                         Non aut et et esse qui sit modi neque. Exercitationem et eos aspernatur. Ea est consequuntur officia beatae ea aut eos soluta. Non qui dolorum voluptatibus et optio veniam. Quam officia sit nostrum dolorem.
                       </p>
                     </div>
                   </div>
   
                 </div>
   
                 <div id="comment-4" className="comment">
                   <div className="d-flex">
                     <div className="comment-img"><img src="/assets/img/blog/comments-6.jpg" alt=""/></div>
                     <div>
                       <h5><a href="">Kay Duggan</a> <a href="#" className="reply"><i className="fa-solid fa-reply"></i> Reply</a></h5>
                       <time dateTime="2020-01-01">01 Jan,2022</time>
                       <p>
                         Dolorem atque aut. Omnis doloremque blanditiis quia eum porro quis ut velit tempore. Cumque sed quia ut maxime. Est ad aut cum. Ut exercitationem non in fugiat.
                       </p>
                     </div>
                   </div>
   
                 </div>
   
               </div>
   
             </section>
   
            
           <CommentFormComponent />
   
           </div>
   
           <div className="col-lg-4 sidebar">
   
             <div className="widgets-container">
   
              
               <div className="search-widget widget-item">
   
                 <h3 className="widget-title">Search</h3>
                 <form action="">
                   <input type="text"/>
                   <button type="submit" title="Search"><i className="fa-brands fa-searchengin"></i></button>
                 </form>
   
               </div>
   
              
               <div className="categories-widget widget-item">
   
                 <h3 className="widget-title">Categories</h3>
                 <ul className="mt-3">
                   <li><a href="#">General <span>(25)</span></a></li>
                   <li><a href="#">Lifestyle <span>(12)</span></a></li>
                   <li><a href="#">Travel <span>(5)</span></a></li>
                   <li><a href="#">Design <span>(22)</span></a></li>
                   <li><a href="#">Creative <span>(8)</span></a></li>
                   <li><a href="#">Educaion <span>(14)</span></a></li>
                 </ul>
   
               </div>
   
               
               <div className="recent-posts-widget widget-item">
   
                 <h3 className="widget-title">Recent Posts</h3>
   
                 <div className="post-item">
                   <img src="/assets/img/blog/blog-recent-1.jpg" alt="" className="flex-shrink-0"/>
                   <div>
                     <h4><a href="blog-details.html">Nihil blanditiis at in nihil autem</a></h4>
                     <time dateTime="2020-01-01">Jan 1, 2020</time>
                   </div>
                 </div>
   
                 <div className="post-item">
                   <img src="/assets/img/blog/blog-recent-2.jpg" alt="" className="flex-shrink-0"/>
                   <div>
                     <h4><a href="blog-details.html">Quidem autem et impedit</a></h4>
                     <time dateTime="2020-01-01">Jan 1, 2020</time>
                   </div>
                 </div>
   
                 <div className="post-item">
                   <img src="/assets/img/blog/blog-recent-3.jpg" alt="" className="flex-shrink-0"/>
                   <div>
                     <h4><a href="blog-details.html">Id quia et et ut maxime similique occaecati ut</a></h4>
                     <time dateTime="2020-01-01">Jan 1, 2020</time>
                   </div>
                 </div>
   
                 <div className="post-item">
                   <img src="/assets/img/blog/blog-recent-4.jpg" alt="" className="flex-shrink-0"/>
                   <div>
                     <h4><a href="blog-details.html">Laborum corporis quo dara net para</a></h4>
                     <time dateTime="2020-01-01">Jan 1, 2020</time>
                   </div>
                 </div>
   
                 <div className="post-item">
                   <img src="/assets/img/blog/blog-recent-5.jpg" alt="" className="flex-shrink-0"/>
                   <div>
                     <h4><a href="blog-details.html">Et dolores corrupti quae illo quod dolor</a></h4>
                     <time dateTime="2020-01-01">Jan 1, 2020</time>
                   </div>
                 </div>
   
               </div>
               <div className="tags-widget widget-item">
   
                 <h3 className="widget-title">Tags</h3>
                 <ul>
                   <li><a href="#">App</a></li>
                   <li><a href="#">IT</a></li>
                   <li><a href="#">Business</a></li>
                   <li><a href="#">Mac</a></li>
                   <li><a href="#">Design</a></li>
                   <li><a href="#">Office</a></li>
                   <li><a href="#">Creative</a></li>
                   <li><a href="#">Studio</a></li>
                   <li><a href="#">Smart</a></li>
                   <li><a href="#">Tips</a></li>
                   <li><a href="#">Marketing</a></li>
                 </ul>
   
               </div>
   
             </div>
   
           </div>
   
         </div>
       </div>
       </>
    );
};

export default BlogDetailsPage;
