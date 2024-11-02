import React, { useState } from 'react';
import { faSteam } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Register from '../../pages/Login/Register';
import Login from '../../pages/Login/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin }) {
    const [isRegister, setIsRegister] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);
    const [errors, setErrors] = useState({});

    const handleRegisterSuccess = () => {
        setIsRegistered(true);
        setIsRegister(false);
    };

    const handleValidation = (field, value) => {
        const newErrors = { ...errors };
        if (field === 'email' && !/\S+@\S+\.\S+/.test(value)) {
            newErrors.email = 'Please enter a valid email.';
        } else if (field === 'password' && value.length < 6) {
            newErrors.password = 'Password must be at least 6 characters.';
        } else {
            delete newErrors[field]; // Clear the error if valid
        }
        setErrors(newErrors);
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
                
                    {isRegister 
                        ? <Register onRegisterSuccess={handleRegisterSuccess} onValidate={handleValidation} errors={errors} />
                        : <Login onLogin={onLogin} onValidate={handleValidation} errors={errors} />
                    }
                
                {/* Steam Login Button */}
                <div className="auth-steam-login">
                    <button className="steam-login-btn">
                        <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                            <FontAwesomeIcon icon={faSteam} size="2x" />
                        </a> 
                       
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AuthModal;
