import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { capitalize } from '../utils/utils.js'; // Adjust path as needed
import './TeamList.css';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; // Placeholder image URL


function TeamList({ team }) {
  const [isOpen, setIsOpen] = useState(false);
  const [scrollingEvents, setScrollingEvents] = useState([]);
  const maxVisibleEvents = 5; // Maximum events visible at one time

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

  useEffect(() => {
    // Load initial state from local storage if available
    const storedState = JSON.parse(localStorage.getItem(`dropdownState-${team.teamName}`));
    if (storedState !== null) {
      setIsOpen(storedState);
    }
  }, [team.teamName]);

  const toggleDropdown = () => {
    const newState = !isOpen;
    setIsOpen(newState);
    // Store the new state in local storage
    localStorage.setItem(`dropdownState-${team.teamName}`, JSON.stringify(newState));
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
        {team.teamName} {isOpen ? '▲' : '▼'}
      </button>
    
      {/* Scrolling Events Container */}
      <div className="scrolling-events-container">
        <div className="scrolling-events">
          {team.events.map((event, index) => (
            <span key={`${event.eventName}-${index}`} className="scrolling-event-text">
              {event.eventName} ({event.eventDate})
            </span>
          ))}
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
                <br></br>
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
