import React, { useEffect, useRef } from 'react';
import './HeroSection.css';
import { gsap } from 'gsap';

function HeroSection() {
    const heroTextRef = useRef(null);
    const registerBtnRef = useRef(null);
    const loginBtnRef = useRef(null);

    useEffect(() => {
        // Animate the text paragraph
        gsap.fromTo(
            heroTextRef.current, 
            { opacity: 0, y: 50 },
            { opacity: 1, y: 0, duration: 1.5, ease: "power2.out", delay: 0.5 }
        );

        // Animate the buttons
        gsap.fromTo(
            registerBtnRef.current, 
            { x: -200, opacity: 0 },
            { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.0 }
        );
        gsap.fromTo(
            loginBtnRef.current, 
            { x: 200, opacity: 0 },
            { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.2 }
        );
    }, []);

    return (
        <section className="hero-section" id="home">
            <div className="hero-content">
                <p ref={heroTextRef}>Manage players, teams, and events easily!</p>
                <div className="cta-buttons">
                    <a href="#register" className="btn primary-btn" ref={registerBtnRef}>Register</a>
                    <a href="#login" className="btn secondary-btn" ref={loginBtnRef}>Login</a>
                </div>
            </div>
        </section>
    );
}

export default HeroSection;
