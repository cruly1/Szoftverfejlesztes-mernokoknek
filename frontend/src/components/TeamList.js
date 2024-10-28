import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './TeamList.css';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; // Path to the placeholder image

function TeamList({ team }) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className="team-item">
      <button className="team-button" onClick={toggleDropdown}>
        {team.teamName} {isOpen ? '▲' : '▼'} {/* Dropdown indicator */}
      </button>
      {isOpen && (
        <ul className="players-dropdown">
          {team.players.map(player => (
            <li key={player} className="player-item">
              <Link to={`/players/${player}`} className="player-link">
                <img 
                  src={placeholderImage} 
                  alt={player}
                  className="player-image"
                />
                {player}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default TeamList;
