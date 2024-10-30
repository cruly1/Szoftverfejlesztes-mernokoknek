import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Players from './pages/Players';
import PlayerDetails from './pages/PlayerDetails';
import Teams from './pages/Teams';
import Events from './pages/Events';
import Profile from './pages/Profile';
import ErrorPage from './pages/ErrorPage'; // Importáld az ErrorPage komponenst

function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <Navbar />
        </header>
        
        <Routes>
          {/* Alapértelmezett oldalak */}
          <Route path="/" element={<Home />} />
          <Route path="/players" element={<Players />} />
          <Route path="/teams" element={<Teams />} />
          <Route path="/events" element={<Events />} />
          <Route path="/players/:id" element={<PlayerDetails />} />
          <Route path="/profile" element={<Profile />} />

          {/* Hibaoldal nem létező útvonalakra */}
          <Route path="*" element={<ErrorPage />} />
        </Routes>
        
        <Footer />
      </div>
    </Router>
  );
}

export default App;
