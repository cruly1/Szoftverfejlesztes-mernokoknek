import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Players from './pages/Players';
import PlayerDetails from './pages/PlayerDetails';
import Teams from './pages/Teams';
import Events from './pages/Events';
import Profile from './pages/Profile';
import ErrorPage from './pages/ErrorPage';
import LoadingCS2 from './components/LoadingCS2';
import EventDetails from './pages/EventDetails';

function App() {
  const [loading, setLoading] = useState(false);
  const location = useLocation();

  useEffect(() => {
    // Trigger loading animation on route change
    setLoading(true);
    const timer = setTimeout(() => {
      setLoading(false); // Hide loading after a short delay
    }, 700); // Adjust delay to fit the animation duration

    return () => clearTimeout(timer); // Clear timeout on component unmount
  }, [location]);

  return (
    
    <div className="App">
      {loading && <LoadingCS2 />} {/* CS2 Loading Animation */}

      <header className="App-header">
        <Navbar />
      </header>

      <Routes>
        <Route path="/" element={<Home />} />
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
