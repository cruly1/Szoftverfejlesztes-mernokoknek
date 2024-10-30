import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { Radar } from 'react-chartjs-2';
import { Chart as ChartJS, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend } from 'chart.js';
import './PlayerDetails.css';
import { capitalize } from '../utils/utils.js'; // Adjust path as needed

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg";



function PlayerDetails() {
  const { id: nickName } = useParams();
  const [player, setPlayer] = useState(null);
  const [teammates, setTeammates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch player data
    axios.get(`http://localhost:8080/api/players/search?nickName=${nickName}`)
      .then(response => {
        const playerData = response.data;
        setPlayer(playerData);
        //ONLY REMOVE COMMENT WHEN BACKEND TEAMNAME SEARCH IS FIXED - Faragou 2024-10-31
        /*--------------------------return playerData.teamName;
      })
      .then(teamName => {
        // Fetch teammates if a team is assigned
        if (teamName) {
          return axios.get(`http://localhost:8080/api/teams/search?teamName=${teamName}`);
        }
      })
      .then(response => {
        if (response) {
          const teamData = response.data;
          // Exclude the current player from teammates list
          const filteredTeammates = teamData.players.filter(teammate => teammate.nickName !== nickName);
          setTeammates(filteredTeammates);
        }--------------------------------------------------------*/
      })
      .catch(err => {
        console.error(err);
        setError("Error fetching player or team data.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [nickName]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;
  if (!player) return <div>No player data found</div>;

  // Placeholder data for the radar chart
  const radarData = {
  labels: ['Aiming', 'Teamwork', 'Communication', 'Game Sense', 'Reflex', 'Strategy'],
  datasets: [
    {
      label: `${player.nickName}'s Skills`,
      data: [80, 70, 75, 85, 90, 65], // Placeholder skill data
      backgroundColor: 'rgba(23, 162, 184, 0.2)', // Light background fill for radar area
      borderColor: '#17a2b8', // Border color for radar area
      borderWidth: 2,
      pointBackgroundColor: '#17a2b8', // Point color
      pointBorderColor: '#fff', // Point border color for better contrast
      pointBorderWidth: 2, // Border width around points
      pointRadius: 4, // Increase point size slightly
      pointHoverRadius: 6, // Increase hover size to make points more visible
    },
  ],
};

const radarOptions = {
  responsive: true,
  maintainAspectRatio: false, // Allows custom sizing in container
  scales: {
    r: {
      beginAtZero: true,
      max: 100,
      ticks: {
        stepSize: 25,
        color: '#fff',
        backdropColor: 'rgba(0, 0, 0, 0)', // Removes background behind tick labels
      },
      grid: {
        color: '#555', // Gridline color
      },
      angleLines: {
        color: '#555', // Angle line color
      },
    },
  },
  plugins: {
    legend: {
      labels: {
        color: '#fff', // Legend label color
      },
    },
  },
};


  return (
    <div className="player-detail">
  <div className="player-header">
    <img src={placeholderImage} alt={`${player.firstName} ${player.lastName}`} className="player-icon" />
    <div className="player-name">
      <h1>{player.firstName} "<span>{player.nickName}</span>" {player.lastName}</h1>
      <img 
        src={`https://flagcdn.com/48x36/${player.nationality.toLowerCase()}.png`} 
        alt={player.nationality} 
        className="flag-icon"
      />
    </div>
  </div>

  <hr className="divider" />

  {/* New container for side-by-side layout */}
  <div className="chart-info-container">
    <div className="chart-placeholder">
      <Radar data={radarData} options={radarOptions} />
    </div>
    <div className="player-info-box">
      <p><strong>Ingame Role:</strong> {player.ingameRole === "IGL" || player.ingameRole === "AWP" ? player.ingameRole : capitalize(player.ingameRole)}</p>
      <p><strong>Date of Birth:</strong> {player.dateOfBirth}</p>
      <p><strong>Gender:</strong> {player.gender}</p>
      <p><strong>Team:</strong> {player.teamName || 'No team'}</p>
    </div>
  </div>

  {teammates.length > 0 && (
    <div className="teammates-section">
      <h2>Teammates</h2>
      <ul>
        {teammates.map(teammate => (
          <li key={teammate.nickName} className="teammate-item">
            <Link to={`/players/${teammate.nickName}`} className="teammate-link">
              <img 
                src={placeholderImage} 
                alt={`${teammate.firstName} ${teammate.lastName}`}
                className="teammate-image"
              />
              <div className="teammate-name">{teammate.firstName} "{teammate.nickName}" {teammate.lastName}</div>
              <div className="teammate-role">{capitalize(teammate.ingameRole)}</div>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )}
</div>
  );
}

export default PlayerDetails;
