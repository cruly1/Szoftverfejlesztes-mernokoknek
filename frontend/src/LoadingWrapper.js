import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, useLocation } from 'react-router-dom';
import App from './App';
import LoadingCS2 from './components/LoadingCS2'; // Adjust path if needed

function LoadingWrapper() {
  const [loading, setLoading] = useState(false);
  const location = useLocation();

  useEffect(() => {
    // Show loading animation on route change
    setLoading(true);
    const timer = setTimeout(() => {
      setLoading(false);
    }, 1000); // Adjust duration as needed

    return () => clearTimeout(timer);
  }, [location]);

  return (
    <>
      {loading && <LoadingCS2 />}
      <App />
    </>
  );
}

function WrapperWithRouter() {
  return (
    <Router>
      <LoadingWrapper />
    </Router>
  );
}

export default WrapperWithRouter;
