import React, { useEffect, useRef, useState } from 'react';
import './HeroSection.css';
import AuthModal from '../Modals/AuthModal';
import ProfileSetupModal from '../Modals/ProfileSetupModal';
import { gsap } from 'gsap';

function HeroSection() {
    const heroTextRef = useRef(null);
    const registerBtnRef = useRef(null);
    const loginBtnRef = useRef(null);
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);
    const [isProfileSetupOpen, setIsProfileSetupOpen] = useState(false);

    const handleLogin = (newToken) => {
        setToken(newToken);
        localStorage.setItem('token', newToken);
        setIsAuthModalOpen(false);
        setIsProfileSetupOpen(true);
    };

    const handleLogout = () => {
        setToken(null);
        localStorage.removeItem('token');
    };

    useEffect(() => {
        setTimeout(() => {
            gsap.fromTo(heroTextRef.current, { opacity: 0, y: 50 }, { opacity: 1, y: 0, duration: 1.5, ease: "power2.out", delay: 0.5 });
            gsap.fromTo(registerBtnRef.current, { x: -200, opacity: 0 }, { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.0 });
            gsap.fromTo(loginBtnRef.current, { x: 200, opacity: 0 }, { x: 0, opacity: 1, duration: 1, ease: "power2.out", delay: 1.2 });
        }, 500);
    }, []);

    return (
        <section className="hero-section" id="home">
            <div className="hero-content">
                <p ref={heroTextRef}>Manage players, teams, and events easily!</p>
                <div className="cta-buttons">
                    {token ? (
                        <button onClick={handleLogout}>Logout</button>
                    ) : (
                        <>
                            <button className="btn primary-btn" onClick={() => setIsAuthModalOpen(true)} ref={registerBtnRef}>Register</button>
                            <button className="btn secondary-btn" onClick={() => setIsAuthModalOpen(true)} ref={loginBtnRef}>Login</button>
                        </>
                    )}
                </div>
            </div>
            <AuthModal isOpen={isAuthModalOpen} onClose={() => setIsAuthModalOpen(false)} onLogin={handleLogin} />
            {isProfileSetupOpen && <ProfileSetupModal onClose={() => setIsProfileSetupOpen(false)} />}
        </section>
    );
}

export default HeroSection;
