import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './EventList.css';

function EventList() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [joiningEvent, setJoiningEvent] = useState(false); // For button loading state
  const [participatingEvents, setParticipatingEvents] = useState([]); // Player's participating events
  const [teamName, setTeamName] = useState(null); // Player's team name

  useEffect(() => {
    const token = localStorage.getItem('token');
    const nickName = localStorage.getItem('nickname');

    if (!token || !nickName) {
      setError('Unauthorized access. Please log in.');
      setLoading(false);
      return;
    }

    // Fetch all events
    axios
      .get('http://localhost:8080/api/events/getAllEvents', {
        headers: { Authorization: `Bearer ${token}` },
        withCredentials: true,
      })
      .then((response) => {
        setEvents(response.data);
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to fetch events.");
      });

    // Fetch player's details
    axios
      .get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        const playerData = response.data;
        setParticipatingEvents(playerData.events.map((event) => event.eventName)); // Store player's participating events
        setTeamName(playerData.teamName || null); // Store player's team name
      })
      .catch((err) => {
        console.error("Failed to fetch player's data:", err);
        setError("Failed to fetch player's data.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleJoinEventAsPlayer = async (eventName) => {
    const nickName = localStorage.getItem('nickname');
    const token = localStorage.getItem('token');

    if (!nickName || !token) {
      alert('Player not logged in or unauthorized access.');
      return;
    }

    setJoiningEvent(true);

    try {
      await axios.put(
        `http://localhost:8080/api/players/addToEvent/search?nickName=${nickName}&eventName=${eventName}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      alert(`You have successfully joined the event: ${eventName} as a player!`);
      setParticipatingEvents([...participatingEvents, eventName]); // Add event to participating events
    } catch (err) {
      console.error("Error joining event as player:", err);
      alert('Failed to join the event. Please try again.');
    } finally {
      setJoiningEvent(false);
    }
  };

  const handleJoinEventAsTeam = async (eventName) => {
    const token = localStorage.getItem('token');
    
    if (!teamName) {
      alert("You don't belong to a team. Unable to join the event as a team.");
      return;
    }

    setJoiningEvent(true);

    try {
      await axios.put(
        `http://localhost:8080/api/teams/addToEvent/search?teamName=${teamName}&eventName=${eventName}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      alert(`Your team (${teamName}) has successfully joined the event: ${eventName}!`);
    } catch (err) {
      console.error("Error joining event as team:", err);
      alert('Failed to join the event as a team. Please try again.');
    } finally {
      setJoiningEvent(false);
    }
  };

  

  if (loading) return <div>Loading events...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="event-list">
      {events.map((event) => (
        <div key={event.eventName} className="event-item">
          <Link to={`/events/${event.eventName}`} className="event-link">
            <h2>{event.eventName}</h2>
            <p>
              <strong>Start Date:</strong> {event.eventStartDate}
            </p>
            <p>
              <strong>End Date:</strong> {event.eventEndDate}
            </p>
          </Link>
          <div className="event-actions">
            {participatingEvents.includes(event.eventName) ? (
              <button
                className="leave-event-button"
                disabled={joiningEvent}
              >
                {joiningEvent ? 'Leaving...' : 'Leave Event'}
              </button>
            ) : (
              <>
                <button
                  className="join-event-player-button"
                  onClick={() => handleJoinEventAsPlayer(event.eventName)}
                  disabled={joiningEvent}
                >
                  {joiningEvent ? 'Joining...' : 'Join as Player'}
                </button>
                {teamName && (
                  <button
                    className="join-event-team-button"
                    onClick={() => handleJoinEventAsTeam(event.eventName)}
                    disabled={joiningEvent}
                  >
                    {joiningEvent ? 'Joining...' : 'Join as Team'}
                  </button>
                )}
              </>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}

export default EventList;
