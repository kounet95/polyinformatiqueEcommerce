import React from 'react';
import { useRoutes } from 'react-router-dom';

import { PrivateRoute} from './­PrivateRoute';
// Import des pages
import HomePage from './pages/website/HomePage';
import ContactPage from './pages/website/ContactPage';
import TeamPage from './pages/website/TeamPage';
import BlogListComponent from './components/website/BlogListComponent';
import BlogDetailsPage from './pages/website/BlogDetailsPage';
import CommentFormComponent from './components/website/CommentFormComponent';

import BlogFormComponent from './components/website/BlogFormComponent';
import DeleteArticleComponent from './components/website/DeleteArticleComponent';

function App() {
  const routes = useRoutes([
    { path: '/', element: <HomePage /> },
    { path: '/contact', element: <ContactPage /> },

    { path: '/team', element: <TeamPage /> },
    { path: '/blog', element: <BlogListComponent /> },
    { path: '/blog/:id', element: <BlogDetailsPage /> },
    

    {
      path: '/blog/new',
      element: (
        <PrivateRoute>
          <CommentFormComponent />
        </PrivateRoute>
      ),
    },
    {
      path: '/domain/new',
      element: (
        <PrivateRoute>
          <DeleteArticleComponent />
        </PrivateRoute>
      ),
    },
    {
      path: '/domain',
      element: (
        <PrivateRoute>
          <BlogFormComponent/>
        </PrivateRoute>
      ),
    },

   
  ]);

  return routes;
}

export default App;
