import React, { useEffect, useState } from 'react';
import { useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import './PlayerDetails.css'; // Assuming you have a CSS file for styling

function PlayerDetails() {
  const { id } = useParams(); // Get the player ID from the URL
  const [player, setPlayer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/api/players/${id}`) // Fetch player data by ID
      .then(response => {
        setPlayer(response.data); // Assuming the response contains player details
        console.log(response.data);
        navigate(`/players/${response.data.firstName}`);
      })
      .catch(err => {
        console.log(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [id, navigate]); // The effect runs whenever the ID in the URL changes

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;
  if (!player) return <div>No player data found</div>;

  return (
    <div className="player-detail">
      <h1>{player.firstName} {player.lastName}</h1>
      <p><strong>Date of Birth:</strong> {player.dateOfBirth}</p>
      <p><strong>Team:</strong> {player.teamName ? player.teamName : 'No team'}</p>
    </div>
  );
}

export default PlayerDetails;
