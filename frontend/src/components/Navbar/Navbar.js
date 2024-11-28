import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar({ loggedIn, onLogout, onProfileComplete}) {
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    const closeMenu = () => {
        setMenuOpen(false); // Close the menu when a link is clicked
    };

    return (
        <nav className="navbar">
            <div className="logo">
                <img src={`${process.env.PUBLIC_URL}/aaa.png`} alt="Logo" />
            </div>
            <div className={`hamburger ${menuOpen ? 'active' : ''}`} onClick={toggleMenu}>
                <div></div>
                <div></div>
                <div></div>
            </div>
            <ul className={`nav-links ${menuOpen ? 'active' : ''}`}>
                <li><Link to="/" className="nav-link" onClick={closeMenu}>Home</Link></li>
                <li><Link to="/players" className="nav-link" onClick={closeMenu}>Players</Link></li>
                <li><Link to="/teams" className="nav-link" onClick={closeMenu}>Teams</Link></li>
                <li><Link to="/events" className="nav-link" onClick={closeMenu}>Events</Link></li>
                {/* Conditionally render Profile link and Logout button */}
                {loggedIn && (
                    <>
                        <li><Link to="/profile" className="nav-link" onClick={closeMenu}>Profile</Link></li>
                        <li><button onClick={() => {onLogout(); closeMenu();}} className="nav-link logout-button">Logout</button></li>
                    </>
                )}
            </ul>
        </nav>
    );
}

export default Navbar;
