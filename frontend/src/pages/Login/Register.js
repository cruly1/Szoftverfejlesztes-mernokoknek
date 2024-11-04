import React, { useState } from 'react';
import axios from 'axios';

function Register({ onRegisterSuccess }) {
    const [formData, setFormData] = useState({ username: '', password: '', email: '' });
    const [error, setError] = useState(null);
    const [emailError, setEmailError] = useState(null);
    const [isEmailValid, setIsEmailValid] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        
        // Validate email format
        if (name === 'email') {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            setIsEmailValid(emailRegex.test(value));
            setEmailError(!emailRegex.test(value) ? 'Please enter a valid email address.' : null);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Check for email validation before proceeding
        if (emailError) {
            return;
        }

        try {
            await axios.post('http://localhost:8080/api/auth/register', formData);
            onRegisterSuccess();
        } catch (err) {
            setError('Registration failed. Please try again.');
        }
    };

    return (
        <div>
            <h2>Register</h2>
            {error && <p className="auth-error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    className={`auth-input ${isEmailValid ? 'valid' : 'invalid'}`}
                    name="username"
                    type="text"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Username"
                    required
                />
                <input
                    className={`auth-input ${isEmailValid ? 'valid' : 'invalid'}`}
                    name="email"
                    type="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Email"
                    required
                />
                {emailError && <p className="auth-error-message">{emailError}</p>}
                <input
                    className="auth-input"
                    name="password"
                    type="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Password"
                    required
                />
                <button type="submit" className="auth-submit-btn">Register</button>
            </form>
        </div>
    );
}

export default Register;
