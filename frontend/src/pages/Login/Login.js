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
      onLogin(token, credentials.username); // Pass token and username
    } catch (err) {
      setError('Authentication failed. Please check your credentials.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <p>{error}</p>}
      <form onSubmit={handleSubmit}>
        <input name="username" type="text"value={credentials.username} onChange={handleChange} placeholder="Username" required />
        <input name="password" type="password" value={credentials.password} onChange={handleChange} placeholder="Password" required />
        
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
