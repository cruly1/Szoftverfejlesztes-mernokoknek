import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { capitalize } from '../utils/utils.js'; 
import './TeamList.css';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; 

function TeamList({ team }) {
  const [isOpen, setIsOpen] = useState(false);
  const [scrollingEvents, setScrollingEvents] = useState([]);

  useEffect(() => {
    // Duplicate events to create an infinite scroll illusion
    setScrollingEvents([...team.events, ...team.events]);
  }, [team.events]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      // Move the first event to the end to create the loop
      setScrollingEvents(prevEvents => {
        const [firstEvent, ...rest] = prevEvents;
        return [...rest, firstEvent];
      });
    }, 3000); // Adjust timing for scroll speed
    return () => clearInterval(intervalId); // Cleanup interval on component unmount
  }, [scrollingEvents]);

  const toggleDropdown = () => {
    const newState = !isOpen;
    setIsOpen(newState);
    localStorage.setItem(`dropdownState-${team.teamName}`, JSON.stringify(newState));
  };

  // Sort players to place "COACH" at the end
  const sortedPlayers = [...team.players].sort((a, b) => {
    if (a.ingameRole === "COACH") return 1;
    if (b.ingameRole === "COACH") return -1;
    return 0;
  });

  return (
    <div className="team-item">
      <div className="team-header">
        <button className="team-button" onClick={toggleDropdown}>
          {team.teamName} {isOpen ? '▲' : '▼'}
        </button>
      
        <div className="scrolling-events-container">
          <div className="scrolling-events">
            {scrollingEvents.map((event, index) => (
              <span key={`${event.eventName}-${index}`} className="scrolling-event-text">
                {event.eventName} ({event.eventDate})
              </span>
            ))}
          </div>
        </div>
      </div>

      {isOpen && (
        <ul className="players-dropdown">
          {sortedPlayers.map(player => (
            <li key={player.nickName} className="player-item">
              <Link to={`/players/${player.nickName}`} className="team-player-link">
                <img 
                  src={placeholderImage} 
                  alt={`${player.firstName} ${player.lastName}`}
                  className="player-image"
                />
                <div className="player-nickname">{player.nickName}</div>
                <div className="player-role">
                  {player.ingameRole === "IGL" || player.ingameRole === "AWP" ? player.ingameRole : capitalize(player.ingameRole)}
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
