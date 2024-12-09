import React from 'react';
import { Link } from 'react-router-dom';
import './LoginRequiredPage.css'; // Add custom CSS if needed

const LoginRequiredPage = () => {
  return (
    <div className="login-required-page">
      <h1>Login Required</h1>
      <p>You need to log in to access this page.</p>
      <Link to="/" className="back-home">Back to Home</Link>
    </div>
  );
};

export default LoginRequiredPage;
