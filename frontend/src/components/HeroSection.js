import React from 'react';
import './HeroSection.css';

function HeroSection() {
    return (
        <section className="hero-section" id="home">
            <div className="hero-content">
                <h1>Welcome to manageYself page</h1>
                <p>Manage players, teams, and events easily!</p>
                <div className="cta-buttons">
                    <a href="#register" className="btn primary-btn">Register</a>
                    <a href="#login" className="btn secondary-btn">Login</a>
                </div>
            </div>
        </section>
    );
}

export default HeroSection;
