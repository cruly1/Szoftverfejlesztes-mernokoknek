import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="logo">
        <img 
        src={`${process.env.PUBLIC_URL}/aaa.png`}  
          />
      </div>
      <ul className="nav-links">
        <li><Link to="/" className="nav-link">Home</Link></li>
        <li><Link to="/players" className="nav-link">Players</Link></li>
        <li><Link to="/teams" className="nav-link">Teams</Link></li>
        <li><Link to="/events" className="nav-link">Events</Link></li>
        <li><Link to="/profile" className="nav-link">Profile</Link></li>
      </ul>
    </nav>
  );
}

export default Navbar;
