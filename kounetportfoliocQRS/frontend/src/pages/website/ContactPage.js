import React from 'react';
import CommentFormComponent from '../../components/website/CommentFormComponent';
import HeaderComponent from '../../components/website/HeaderComponent';
import ContactFormComponent from "../../components/lecontinent/ContactFormComponent";

const ContactPage = () => {
    return (
        <>
            <div className="page-title" data-aos="fade">
                <HeaderComponent title="Contact" />
                <nav className="breadcrumbs">
                    <div className="container">
                        <ol>
                            <li><a href="/frontend/public">Accueil</a></li>
                        <li className="current">Contact</li>
                        </ol>
                    </div>
                </nav>
            </div>

            <ContactFormComponent />
        </>
    );
};

export default ContactPage;
