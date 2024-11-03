import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './EventDetails.css';

function EventDetails() {
  const { eventName } = useParams(); // Get the eventName from the URL
  const [event, setEvent] = useState(null);
  const [players, setPlayers] = useState([]);
  const [teams, setTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch event details
  useEffect(() => {
    const token = localStorage.getItem('token'); 
    axios.get(`http://localhost:8080/api/events/search?eventName=${eventName}`, {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        setEvent(response.data);
      })
      .catch(err => {
        console.error(err);
        setError("Error fetching event details.");
      });
  }, [eventName]);

  // Fetch players and teams to find participants
  useEffect(() => {
    const token = localStorage.getItem('token'); 
    // Fetch players
    axios.get("http://localhost:8080/api/players/getAllPlayers", {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        const allPlayers = response.data;
        const participatingPlayers = allPlayers.filter(player =>
          player.events.some(event => event.eventName === eventName)
        );
        setPlayers(participatingPlayers);
      })
      .catch(err => {
        console.error(err);
      });

    // Fetch teams
    axios.get("http://localhost:8080/api/teams/getAllTeams", {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        const allTeams = response.data;
        const participatingTeams = allTeams.filter(team =>
          team.events.some(event => event.eventName === eventName)
        );
        setTeams(participatingTeams);
      })
      .catch(err => {
        console.error(err);
      })
      .finally(() => setLoading(false));
  }, [eventName]);

  if (loading) return <div>Loading event details...</div>;
  if (error) return <div>{error}</div>;
  if (!event) return <div>No event data found</div>;

  return (
    <div className="event-detail">
      <h1>{event.eventName}</h1>
      <p><strong>Start Date:</strong> {event.eventStartDate}</p>
      <p><strong>End Date:</strong> {event.eventEndDate}</p>

      {/* Players Section */}
      <div className="participants-section">
        <h2>Participating Players</h2>
        {players.length > 0 ? (
          <ul>
            {players.map(player => (
              <li key={player.nickName}>
                {player.firstName} "{player.nickName}" {player.lastName} - {player.ingameRole}
              </li>
            ))}
          </ul>
        ) : (
          <p>No players have joined this event yet.</p>
        )}
      </div>

      {/* Teams Section */}
      <div className="participants-section">
        <h2>Participating Teams</h2>
        {teams.length > 0 ? (
          <ul>
            {teams.map(team => (
              <li key={team.teamName}>
                <strong>{team.teamName}</strong>
                <ul>
                  {team.players.map(player => (
                    <li key={player.nickName}>
                      {player.firstName} "{player.nickName}" {player.lastName} - {player.ingameRole}
                    </li>
                  ))}
                </ul>
              </li>
            ))}
          </ul>
        ) : (
          <p>No teams have joined this event yet.</p>
        )}
      </div>
    </div>
  );
}

export default EventDetails;
