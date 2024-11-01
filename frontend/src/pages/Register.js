import React, { useState } from 'react';
import axios from 'axios';

function Register() {
  const [formData, setFormData] = useState({ username: '', password: '', email: '' });
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(formData);
    try {
      await axios.post('http://localhost:8080/api/auth/register', formData);
      setSuccess(true);
    } catch (err) {
      setError('Registration failed. Please try again.');
    }
  };

  return (
    <div>
      <h2>Register</h2>
      {success && <p>Registration successful! You can now log in.</p>}
      {error && <p>{error}</p>}
      <form onSubmit={handleSubmit}>
        <input name="username" value={formData.username} onChange={handleChange} placeholder="Username" required />
        <input name="password" type="password" value={formData.password} onChange={handleChange} placeholder="Password" required />
        <input name="email" value={formData.email} onChange={handleChange} placeholder="Email" required />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;
