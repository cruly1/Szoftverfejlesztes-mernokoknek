import React, { useState } from 'react';
import axios from 'axios';

function Register({ onRegisterSuccess }) {
    const [formData, setFormData] = useState({ username: '', password: '', email: '' });
    const [error, setError] = useState(null);
    const [emailError, setEmailError] = useState(null);

    // Email validation function
    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
        return emailRegex.test(email);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });

        if (name === 'email') {
            if (!validateEmail(value)) {
                setEmailError('Please enter a valid email address');
            } else {
                setEmailError(null);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (emailError) return; // Prevent submission if email is invalid
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
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    name="username"
                    type="text"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Username"
                    required
                />
                <input
                    name="password"
                    type="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Password"
                    required
                />
                <input
                    name="email"
                    type="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Email"
                    required
                    className={emailError ? 'input-error' : 'input-valid'}
                />
                {emailError && <p className="error-message">{emailError}</p>}
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Register;
