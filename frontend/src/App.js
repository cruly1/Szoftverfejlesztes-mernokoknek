import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';
import Home from './pages/Home/Home';
import Players from './pages/Players/Players';
import PlayerDetails from './pages/Players/PlayerDetails/PlayerDetails';
import Teams from './pages/Teams/Teams';
import Events from './pages/Events/Events';
import Profile from './pages/Profile/Profile';
import ErrorPage from './pages/ErrorPage/ErrorPage';
import LoadingCS2 from './components/LoadingCS2/LoadingCS2';
import EventDetails from './pages/Events/EventDetails/EventDetails';
import axios from 'axios';

axios.defaults.withCredentials = true;

function App() {
  const [loading, setLoading] = useState(false);
  const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem('token'));
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // Trigger loading animation on route change
    setLoading(true);
    const timer = setTimeout(() => {
      setLoading(false); // Hide loading after a short delay
    }, 700);

    return () => clearTimeout(timer);
  }, [location]);

  // Define handleLogin function
  const handleLogin = (token) => {
    localStorage.setItem('token', token);
    setLoggedIn(true); // Update loggedIn state immediately
  };

  // Function to handle logout
  const handleLogout = () => {
    localStorage.removeItem('token');
    setLoggedIn(false); // Update loggedIn state immediately
    navigate("/"); // Redirect to home page after logout
  };

  const handleProfileSetupComplete = () => {
    setLoggedIn(true); // Update the loggedIn state after profile setup
  };

  return (
    <div className="App">
      {loading && <LoadingCS2 />} {/* CS2 Loading Animation */}

      <header className="App-header">
        <Navbar loggedIn={loggedIn} onLogout={handleLogout} />
      </header>

      <Routes>
        <Route path="/" element={<Home onLogin={handleLogin} loggedIn={loggedIn} onProfileSetupComplete={handleProfileSetupComplete}/>} />
        <Route path="/players" element={<Players />} />
        <Route path="/teams" element={<Teams />} />
        <Route path="/events" element={<Events />} />
        <Route path="/players/:id" element={<PlayerDetails />} />
        <Route path="/events/:eventName" element={<EventDetails />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="*" element={<ErrorPage />} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
