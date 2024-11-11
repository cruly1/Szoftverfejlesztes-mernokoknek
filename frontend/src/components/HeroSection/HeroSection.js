import React, { useEffect, useRef, useState } from 'react';
import './HeroSection.css';
import AuthModal from '../Modals/AuthModal';
import ProfileSetupModal from '../Modals/ProfileSetupModal';
import { gsap } from 'gsap';
import axios from 'axios';

function HeroSection({ onLogin, loggedIn, onProfileComplete }) {
    const heroTextRef = useRef(null);
    const registerBtnRef = useRef(null);
    const loginBtnRef = useRef(null);
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [username, setUsername] = useState(null);
    const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);
    const [isProfileSetupOpen, setIsProfileSetupOpen] = useState(false);
    const [initialView, setInitialView] = useState("register");

    const handleLogin = async (newToken, username) => {
        setToken(newToken);
        setUsername(username);
        localStorage.setItem('token', newToken);
        setIsAuthModalOpen(false);
        setIsProfileSetupOpen(true);
         
        try {
            // Check if the user already has a player
            const response = await axios.get(`http://localhost:8080/api/players/getByUsername/search?username=${username}`, {
            headers: { Authorization: `Bearer ${newToken}` },
            withCredentials: true
            });
            const player = response.data;

            if (!player) {
                // If no player exists, open ProfileSetupModal
                setIsProfileSetupOpen(true);
            } else {
                // If player exists, close any open modals
                setIsProfileSetupOpen(false);
            }
            onLogin(newToken);
        } catch (error) {
            console.error("Error checking for existing player:", error);
        }
    };

    const handleLogout = () => {
        setToken(null);
        setUsername(null);
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
                    {loggedIn ? null: (
                        <>
                            <button className="btn primary-btn" onClick={() => { setInitialView("register"); setIsAuthModalOpen(true);}} ref={registerBtnRef}>Register</button>
                            <button className="btn secondary-btn" onClick={() => { setInitialView("login"); setIsAuthModalOpen(true);}} ref={loginBtnRef}>Login</button>
                        </>
                    )}
                </div>
            </div>
            <AuthModal isOpen={isAuthModalOpen} onClose={() => setIsAuthModalOpen(false)} onLogin={handleLogin} initialView={initialView}/>
            {isProfileSetupOpen && (
                <ProfileSetupModal
                    username={username}
                    onClose={() => setIsProfileSetupOpen(false)}
                    onProfileComplete={() => handleLogin(token, username)} // Call handleLogin directly here
                />
            )}
        </section>
    );
}

export default HeroSection;
