// src/pages/ErrorPage.js
import React from 'react';
import { Link } from 'react-router-dom';
import './ErrorPage.css';

const ErrorPage = () => {
  return (
    <div className="error-page">
      <h1>404</h1>
      <p>Oops! The page you are looking for does not exist.</p>
      <Link to="/" className="back-home">Go Back to Home</Link>
    </div>
  );
};

export default ErrorPage;
