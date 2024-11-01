import React from 'react';
import { useState } from 'react';
import Register from '../pages/Register';
import Login from '../pages/Login';
import './AuthModal.css';

function AuthModal({ isOpen, onClose, onLogin }) {
    const [isRegister, setIsRegister] = useState(true);

    if (!isOpen) return null;

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="close-btn" onClick={onClose}>X</button>
                <div className="modal-toggle">
                    <button onClick={() => setIsRegister(true)} className={isRegister ? 'active' : ''}>Register</button>
                    <button onClick={() => setIsRegister(false)} className={!isRegister ? 'active' : ''}>Login</button>
                </div>
                {isRegister ? <Register /> : <Login onLogin={onLogin} />}
            </div>
        </div>
    );
}

export default AuthModal;
