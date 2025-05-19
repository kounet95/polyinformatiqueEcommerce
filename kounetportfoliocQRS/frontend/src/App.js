import {BrowserRouter, Route, Routes, useRoutes} from "react-router-dom";
import React from "react";
import HomePage from "./pages/website/HomePage";
import ContactPage from "./pages/website/ContactPage";
import PricingPage from "./pages/website/PricingPage";
import ServicePage from "./pages/website/ServicePage";
import TeamPage from "./pages/website/TeamPage";



import BlogDetailsPage from "./pages/website/BlogDetailsPage";
import BlogFormPage from "./pages/website/BlogFormPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import ForgotPasswordPage from "./pages/website/ForgotPasswordPage";
import DomainFormPage from "./pages/website/DomainFormPage";
import DomainPage from "./pages/website/DomainPage";

import SignInSignUpPage from "./pages/SignInSignUpPage";
import CartPage from "./pages/lecontinent/CartPage";
import DetailPage from "./pages/lecontinent/DetailPage";
import BlogListComponent from "./components/website/BlogListComponent";
import BlogFormComponent from "./components/website/BlogFormComponent";
import CommentFormComponent from "./components/website/CommentFormComponent";
import BlogDetailComponent from "./components/website/BlogDetailComponent";
import DomainListComponent from "./components/website/DomainListComponent";

function App() {
    const routes = useRoutes([
        { path: '/', element: <HomePage /> },
        { path: '/contact', element: <ContactPage /> },
        { path: '/pricing', element: <PricingPage /> },
        { path: '/services', element: <ServicePage /> },
        { path: '/faireCommentaire', element: <CommentFormComponent /> },
        { path: '/domains', element: <DomainListComponent /> },
        { path: '/services', element: <ServicePage /> },
        { path: '/team', element: <TeamPage /> },
        { path: '/blog', element: <BlogListComponent /> },
        { path: '/blog/:id', element: <BlogDetailComponent /> },
        { path: '/blog/new', element: <BlogFormComponent /> },
        { path: '/domain/new', element: <DomainFormPage /> },
        { path: '/domain', element: <DomainPage /> },
        { path: '/cart', element: <CartPage /> },
        { path: '/lecontinent', element: <DetailPage /> },

        { path: '/sign', element: <SignInSignUpPage /> },

        { path: '/forgotten', element: <ForgotPasswordPage /> },

    ]);

    return routes;
}

export default App
