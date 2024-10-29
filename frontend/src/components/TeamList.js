import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './TeamList.css';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; // Path to the placeholder image

function TeamList({ team }) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  // Sort players to place "COACH" at the end
  const sortedPlayers = [...team.players].sort((a, b) => {
    if (a.ingameRole === "COACH") return 1; // Move COACH to the end
    if (b.ingameRole === "COACH") return -1;
    return 0; // Otherwise, maintain the existing order
  });

  return (
    <div className="team-item">
      <button className="team-button" onClick={toggleDropdown}>
        {team.teamName} {isOpen ? '▲' : '▼'} {/* Dropdown indicator */}
      </button>
      {isOpen && (
        <ul className="players-dropdown">
          {sortedPlayers.map(player => (
            <li key={player.nickName} className="player-item">
              <Link to={`/players/${player.nickName}`} className="player-link">
                <img 
                  src={placeholderImage} 
                  alt={`${player.firstName} ${player.lastName}`}
                  className="player-image"
                />
                <div className="player-nickname">"{player.nickName}"</div>
                <div className="player-role">
                  {player.ingameRole === "IGL" ? player.ingameRole : player.ingameRole.toLowerCase()}
                </div>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default TeamList;
