import React, { useState } from 'react';
import axios from 'axios';


function Login({ onLogin }) {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/authenticate', credentials, { withCredentials: true });
      const { token } = response.data;
      onLogin(token, credentials.username);
      setError(null); // Clear any previous error on successful login
    } catch (err) {
      setError('Authentication failed. Please check your credentials.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <p className="auth-error-message">{error}</p>}
      <form onSubmit={handleSubmit}>
        <input
          className={`auth-input ${credentials.username ? 'valid' : ''}`}
          name="username"
          type="text"
          value={credentials.username}
          onChange={handleChange}
          placeholder="Username"
          required
        />
        <input
          className={`auth-input ${credentials.password ? 'valid' : ''}`}
          name="password"
          type="password"
          value={credentials.password}
          onChange={handleChange}
          placeholder="Password"
          required
        />
        <button type="submit" className="auth-submit-btn">Login</button>
      </form>
    </div>
  );
}

export default Login;
