import React, { useEffect, useRef } from 'react';
import './HeroSection.css';
import { gsap } from 'gsap'; // Import GSAP

function HeroSection() {
    const heroTitleRef = useRef(null);
    const heroTextRef = useRef(null);
    const registerBtnRef = useRef(null);
    const loginBtnRef = useRef(null);

    useEffect(() => {
        // Manually split the title into characters by wrapping each letter in a <span>
        const titleChars = heroTitleRef.current.innerText.split('').map(char => `<span>${char}</span>`).join('');
        heroTitleRef.current.innerHTML = titleChars;

        // Animate each character of the title (character by character)
        gsap.fromTo(
            heroTitleRef.current.children, 
            { opacity: 0, y: -50 }, // Initial state: invisible and above
            { opacity: 1, y: 0, duration: 1, stagger: 0.05, ease: "power2.out" } // Staggered animation
        );

        // Animate the text paragraph (letter by letter)
        gsap.fromTo(
            heroTextRef.current, 
            { opacity: 0, y: 50 }, // Initial state: invisible and below
            { opacity: 1, y: 0, duration: 1.5, ease: "power2.out", delay: 1 } // Final state
        );

        // Animate the buttons (Register from left, Login from right)
        gsap.fromTo(
            registerBtnRef.current, 
            { x: -200, opacity: 0 }, // Start off-screen to the left
            { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.5 } // Slide in from the left
        );
        gsap.fromTo(
            loginBtnRef.current, 
            { x: 200, opacity: 0 }, // Start off-screen to the right
            { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.8 } // Slide in from the right
        );
    }, []);

    return (
        <section className="hero-section" id="home">
            <div className="hero-content">
                <h1 ref={heroTitleRef}>Welcome to manageYself Website</h1>
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
