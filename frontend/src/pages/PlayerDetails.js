import React, { useEffect, useState } from 'react';
import { useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import './PlayerDetails.css'; 

function PlayerDetails() {
  const { id: nickName } = useParams(); 
  const [player, setPlayer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  

  useEffect(() => {
    axios.get(`http://localhost:8080/api/players/search?nickName=${nickName}`)
      .then(response => {
        setPlayer(response.data); 
        
      })
      .catch(err => {
        console.log(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [nickName]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;
  if (!player) return <div>No player data found</div>;

  return (
    <div className="player-detail">
      <h1>{player.firstName} {player.lastName}</h1>
      <p><strong>Date of Birth:</strong> {player.birthDate}</p>
       <p><strong>Nickname:</strong> {player.nickName}</p>
      <p><strong>Team:</strong> {player.teamName ? player.teamName : 'No team'}</p>
    </div>
  );
}

export default PlayerDetails;
