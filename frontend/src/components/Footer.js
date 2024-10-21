import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faXTwitter, faInstagram } from '@fortawesome/free-brands-svg-icons';
import './Footer.css';

const Footer = () => {
    return (
        <footer className="footer">
            <p>Follow us on:</p>
            <div className="social-icons">
                <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faFacebook} size="2x" />
                </a>
                <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faXTwitter} size="2x" />
                </a>
                <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                    <FontAwesomeIcon icon={faInstagram} size="2x" />
                </a>
            </div>

            <p>&copy; 2024 Esport Tracker. All rights reserved slakipower and pfaragou</p>
        </footer>
    );
}

export default Footer;
