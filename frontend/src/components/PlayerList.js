import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './PlayerList.css'; // Assuming you have this CSS file

function PlayerList() {
  const [players, setPlayers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch the list of players from the backend
  useEffect(() => {
    axios.get('http://localhost:8080/api/players/getAllPlayers') // Update the URL with your backend endpoint
      .then(response => {
        setPlayers(response.data); // Assuming the response is an array of player objects
      })
      .catch(err => {
        setError('Failed to fetch players');
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="player-list">
      <h1>Our Players</h1>
      <ul>
        {players.map(player => (
          <li key={player.id}>
            {/* Create a link to the player's detail page and display full name */}
            <Link to={`/players/${player.id}`} className="player-link">
                {player.firstName} {player.lastName}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default PlayerList;
