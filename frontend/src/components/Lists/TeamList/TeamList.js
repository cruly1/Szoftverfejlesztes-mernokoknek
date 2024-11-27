import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { capitalize } from '../../../utils/utils.js';
import axios from 'axios'; 
import './TeamList.css';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; 

function TeamList({ team }) {
  const [teams, setTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [scrollingEvents, setScrollingEvents] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (!token) {
      setError('Unauthorized access. Please log in.');
      setLoading(false);
      return;
    }
    
    axios.get("http://localhost:8080/api/teams/getAllTeams", {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        setTeams(response.data);
      })
      .catch(err => {
        console.error(err);
        setError("Error fetching teams.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    
    // Check if team.events exists and is an array; if not, use an empty array
    const events = team.events ? team.events : [];
    setScrollingEvents([...events, ...events]);
  }, [team.events]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setScrollingEvents(prevEvents => {
        const [firstEvent, ...rest] = prevEvents;
        return [...rest, firstEvent];
      });
    }, 3000); 
    return () => clearInterval(intervalId); 
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

  if (loading) return <div>Loading teams...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="team-item">
      <div className="team-header">
        <button className="team-button" onClick={toggleDropdown}>
          {team.teamName} {isOpen ? '▲' : '▼'}
        </button>
      
        {/* Display scrolling events only if there are events */}
        <div className="scrolling-events-container">
          <div className="scrolling-events">
            {scrollingEvents.length > 0 ? (
              scrollingEvents.map((event, index) => (
                event && event.eventName && event.eventDate ? ( // Check if event and properties exist
                  <span key={`${event.eventName}-${index}`} className="scrolling-event-text">
                    {event.eventName} ({event.eventDate})
                  </span>
                ) : null // If the event is undefined or lacks properties, render nothing
              ))
            ) : (
              <span className="no-event-text">No events available</span>
            )}
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
