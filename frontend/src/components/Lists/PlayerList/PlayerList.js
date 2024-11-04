import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './PlayerList.css'; 



function PlayerList() {
  const [players, setPlayers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

 
  useEffect(() => {
    const token = localStorage?.getItem('token'); 

    if (!token) {
      setError('Unauthorized access. Please log in.');
      setLoading(false);
      return;
    }
    
    axios.get('http://localhost:8080/api/players/getAllPlayers', {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        setPlayers(response?.data); 
        
      })
      .catch((error) => {
        console.error("Error fetching players:", error);
        setError(error?.response?.data?.message || 'Failed to fetch players');
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="player-list">
      
      <ul>
        {players?.map(player => (
          <li key={player?.nickName}>
            {/* Create a link to the player's detail page and display full name */}
            <Link to={`/players/${player?.nickName}`} className="player-link">
                {player?.firstName} "{player?.nickName}" {player?.lastName}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default PlayerList;
