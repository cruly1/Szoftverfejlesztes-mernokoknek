import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Radar } from 'react-chartjs-2';
import { Chart as ChartJS, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend } from 'chart.js';
import './PlayerDetails.css';

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg";

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
        console.error(err);
        setError("Error fetching player data.");
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
        backgroundColor: 'rgba(23, 162, 184, 0.2)',
        borderColor: '#17a2b8',
        borderWidth: 2,
        pointBackgroundColor: '#17a2b8',
      },
    ],
  };

  const radarOptions = {
    scales: {
      r: {
        beginAtZero: true,
        max: 100,
        ticks: {
          stepSize: 20,
          color: '#fff'
        },
        grid: {
          color: '#555'
        },
        angleLines: {
          color: '#555'
        },
      },
    },
    plugins: {
      legend: {
        labels: {
          color: '#fff'
        }
      }
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

      <div className="chart-placeholder">
        <Radar data={radarData} options={radarOptions} />
      </div>

      <div className="player-info-box">
        <p><strong>Ingame Role:</strong> {player.ingameRole}</p>
        <p><strong>Date of Birth:</strong> {player.dateOfBirth}</p>
        <p><strong>Gender:</strong> {player.gender}</p>
        <p><strong>Team:</strong> {player.team?.teamName || 'No team'}</p>
      </div>
    </div>
  );
}

export default PlayerDetails;
