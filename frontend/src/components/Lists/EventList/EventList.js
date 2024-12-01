import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './EventList.css';

function EventList() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [joiningEvent, setJoiningEvent] = useState(false);
  const [participatingEvents, setParticipatingEvents] = useState([]);
  const [teamName, setTeamName] = useState(null);

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
        setError('Failed to fetch events.');
      });

    // Fetch player's details
    axios
      .get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        const playerData = response.data;
        const playerEvents = playerData.events.map((event) => ({
          eventName: event.eventName,
          type: event.teamName ? 'team' : 'solo',
        }));
        setParticipatingEvents(playerEvents);
        setTeamName(playerData.teamName || null);
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
      setParticipatingEvents([...participatingEvents, { eventName, type: 'solo' }]);
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
      setParticipatingEvents([...participatingEvents, { eventName, type: 'team' }]);
    } catch (err) {
      console.error("Error joining event as team:", err);
      alert('Failed to join the event as a team. Please try again.');
    } finally {
      setJoiningEvent(false);
    }
  };

  const handleDeleteEvent = async (eventName) => {
    const token = localStorage.getItem('token');

    if (!token) {
      alert('Unauthorized access. Please log in.');
      return;
    }

    const confirmDelete = window.confirm(`Are you sure you want to delete the event: ${eventName}?`);

    if (!confirmDelete) return;

    try {
      await axios.delete(`http://localhost:8080/api/events/deleteEvent/search?eventName=${eventName}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEvents(events.filter((event) => event.eventName !== eventName));
      alert(`Event ${eventName} has been deleted successfully.`);
    } catch (err) {
      console.error("Error deleting event:", err);
      alert('Failed to delete the event. Please try again.');
    }
  };

  if (loading) return <div>Loading events...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="event-list">
      {events.map((event) => {
        const participation = participatingEvents.find(
          (e) => e.eventName === event.eventName
        );

        return (
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
              {participation ? (
                <span className="already-joined-text">
                  Already participating as{' '}
                  {participation.type === 'team' ? `Team (${teamName})` : 'Solo'}
                </span>
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
              <button
                className="delete-event-button"
                onClick={() => handleDeleteEvent(event.eventName)}
              >
                Delete Event
              </button>
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default EventList;
