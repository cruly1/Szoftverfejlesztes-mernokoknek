import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="logo">manageYself</div>
      <ul className="nav-links">
        <li><Link to="/" className="nav-link">Home</Link></li>
        <li><Link to="/players" className="nav-link">Players</Link></li>
        <li><Link to="/teams" className="nav-link">Teams</Link></li>
        <li><Link to="/events" className="nav-link">Events</Link></li>
      </ul>
    </nav>
  );
}

export default Navbar;
