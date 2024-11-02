import React, { useState } from 'react';
import Register from '../../pages/Login/Register';
import Login from '../../pages/Login/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin }) {
    const [isRegister, setIsRegister] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);

    const handleRegisterSuccess = () => {
        setIsRegistered(true);
        setIsRegister(false); // Switch to login after successful registration
    };

    if (!isOpen) return null;

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="close-btn" onClick={onClose}>X</button>
                <div className="modal-toggle">
                    <button onClick={() => setIsRegister(true)} className={isRegister ? 'active' : ''}>Register</button>
                    <button onClick={() => setIsRegister(false)} className={!isRegister ? 'active' : ''}>Login</button>
                </div>
                {isRegistered && <p>Registration successful! You can now log in.</p>}
                {isRegister ? <Register onRegisterSuccess={handleRegisterSuccess} /> : <Login onLogin={onLogin} />}
            </div>
        </div>
    );
}

export default AuthModal;
