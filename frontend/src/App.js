import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Players from './pages/Players';
import PlayerDetails from './pages/PlayerDetails';
import Teams from './pages/Teams';
import Events from './pages/Events';

function App() {
  return (
    <Router>
      <div className="App">
        
        <header className="App-header">
            <Navbar />
        </header>
        <Routes>
          {/* Define Routes for Different Pages */}
          <Route path="/" element={<Home />} />
          <Route path="/players" element={<Players />} />
          <Route path="/teams" element={<Teams />} />
          <Route path="/events" element={<Events />} />
          <Route path="/players/:id" element={<PlayerDetails />} /> {/* Dynamic route for player details */}
        </Routes>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
