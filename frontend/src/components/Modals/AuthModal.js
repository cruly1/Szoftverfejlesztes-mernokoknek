import React, { useState } from 'react';
import Register from '../../pages/Login/Register';
import Login from '../../pages/Login/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin }) {
    const [isRegister, setIsRegister] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);

    const handleRegisterSuccess = () => {
        setIsRegistered(true);
        setIsRegister(false);
    };

    if (!isOpen) return null;

    return (
        <div className="auth-modal-overlay" onClick={onClose}>
            <div className={`auth-modal-content ${isRegister ? 'register' : 'login'}`} onClick={(e) => e.stopPropagation()}>
                <button className="auth-close-btn" onClick={onClose}>X</button>
                <div className="auth-modal-toggle">
                    <button onClick={() => setIsRegister(true)} className={`auth-toggle-btn ${isRegister ? 'active' : ''}`}>Register</button>
                    <button onClick={() => setIsRegister(false)} className={`auth-toggle-btn ${!isRegister ? 'active' : ''}`}>Login</button>
                </div>
                {isRegistered && <p className="auth-success-message">Registration successful! You can now log in.</p>}
                <div className={`auth-form-container ${isRegister ? 'slide-right' : 'slide-left'}`}>
                    {isRegister ? <Register onRegisterSuccess={handleRegisterSuccess} /> : <Login onLogin={onLogin} />}
                </div>
            </div>
        </div>
    );
}

export default AuthModal;
