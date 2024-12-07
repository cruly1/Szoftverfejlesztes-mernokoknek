import React, { useState, useEffect } from 'react';
import { Modal } from 'react-responsive-modal';
import 'react-responsive-modal/styles.css';
import { faSteam } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Register from '../../pages/Login/Register';
import Login from '../../pages/Login/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin }) {
    const [isRegister, setIsRegister] = useState(true);

    useEffect(() => {
        // Add overflow hidden to body when modal is open
        if (isOpen) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = 'auto';
        }
        // Cleanup on component unmount
        return () => (document.body.style.overflow = 'auto');
    }, [isOpen]);

    return (
        <Modal open={isOpen} onClose={onClose} center>
            <div className="auth-modal-content">
                <div className="auth-modal-toggle">
                    <button onClick={() => setIsRegister(true)} className={`auth-toggle-btn ${isRegister ? 'active' : ''}`}>
                        Register
                    </button>
                    <button onClick={() => setIsRegister(false)} className={`auth-toggle-btn ${!isRegister ? 'active' : ''}`}>
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
