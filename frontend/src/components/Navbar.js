import React from 'react';
import './Navbar.css';

function Navbar() {
    return (
        <nav className="navbar">
            <div className="logo">manageYself</div>
            <ul className="nav-links">
                <li><a href="#home">Home</a></li>
                <li><a href="#players">Players</a></li>
                <li><a href="#teams">Teams</a></li>
                <li><a href="#events">Events</a></li>
                <li><a href="#login">Login</a></li>
                <li><a href="#register">Register</a></li>
            </ul>
        </nav>
    );
}

export default Navbar;
