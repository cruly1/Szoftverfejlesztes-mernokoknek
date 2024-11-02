import React, { useState } from 'react';
import axios from 'axios';

function Register({ onRegisterSuccess }) {
    const [formData, setFormData] = useState({ username: '', password: '', email: '' });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
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
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <input name="username" type="text"value={formData.username} onChange={handleChange} placeholder="Username" required />
                <input name="password" type="password" value={formData.password} onChange={handleChange} placeholder="Password" required />
                <input name="email" type="email" value={formData.email} onChange={handleChange} placeholder="Email" required />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Register;
