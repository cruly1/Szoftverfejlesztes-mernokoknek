import React, { useState, useEffect } from 'react';
import { Modal } from 'react-responsive-modal';
import 'react-responsive-modal/styles.css';
import { faSteam } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Register from '../../pages/Login/Register';
import Login from '../../pages/Login/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin, initialView = 'login' }) { // New `initialView` prop
    const [isRegister, setIsRegister] = useState(initialView === 'register');
    const [animate, setAnimate] = useState(false);

    useEffect(() => {
        // Update the state based on `initialView` prop when modal opens
        if (isOpen) {
            setIsRegister(initialView === 'register');
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = 'auto';
        }

        return () => (document.body.style.overflow = 'auto');
    }, [isOpen, initialView]); // Add `initialView` dependency

    // Toggle between Register and Login with animation
    const toggleView = () => {
        setAnimate(true);
        setTimeout(() => {
            setIsRegister(!isRegister);
            setAnimate(false);
        }, 300); // Transition duration in ms
    };

    return (
        <Modal open={isOpen} onClose={onClose} center>
            <div className="auth-modal-content">
                <div className="auth-modal-toggle">
                    <button onClick={toggleView} className={`auth-toggle-btn ${isRegister ? 'active' : ''}`}>
                        Register
                    </button>
                    <button onClick={toggleView} className={`auth-toggle-btn ${!isRegister ? 'active' : ''}`}>
                        Login
                    </button>
                </div>
                
                {isRegister ? (
                    <Register onRegisterSuccess={() => setIsRegister(false)} />
                ) : (
                    <Login onLogin={onLogin} />
                )}

                <div className="auth-steam-login">
                    <button className="steam-login-btn">
                        <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                            <FontAwesomeIcon icon={faSteam} size="2x" />
                        </a>
                    </button>
                </div>
            </div>
        </Modal>
    );
}

export default AuthModal;
