import React, { useEffect, useState, useContext } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { LoadingContext } from '../../../LoadingWrapper';
import { Radar } from 'react-chartjs-2';
import { Chart as ChartJS, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend } from 'chart.js';
import './PlayerDetails.css';
import { capitalize } from '../../../utils/utils.js';

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg";

function PlayerDetails() {
  const { id: nickName } = useParams();
  const [player, setPlayer] = useState(null);
  const [profileImage, setProfileImage] = useState(null);
  const [profileImageName, setProfileImageName] = useState(null);
  const [teammates, setTeammates] = useState([]);
  const [loading, setLoadingLocal] = useState(true);
  const [error, setError] = useState(null);
  const { setLoading } = useContext(LoadingContext);

  useEffect(() => {
    const token = localStorage.getItem('token'); 
    
    setPlayer(null);
    setProfileImage(null); // Clear previous image before fetching
    setLoading(true); // Start loading animation
    axios.get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`, {
        headers: { Authorization: `Bearer ${token}` },
        withCredentials: true
    })
    .then(response => {
        const playerData = response.data;
        setPlayer(playerData);
        setProfileImageName(playerData.profileImageName);
        return playerData.teamName;
    })
    .then(teamName => {
        if (teamName) {
            return axios.get(`http://localhost:8080/api/teams/search?teamName=${teamName}`, {
                headers: { Authorization: `Bearer ${token}` },
                withCredentials: true
            });
        }
    })
    .then(async (response) => {
        if (response) {
            const teamData = response.data;

            const filteredTeammates = await Promise.all(
                teamData.players.filter(teammate => teammate.nickName !== nickName).map(async (teammate) => {
                    if (teammate.profileImageName) {
                        try {
                            const imageResponse = await axios.get(
                                `http://localhost:8080/api/images/${teammate.profileImageName}`,
                                {
                                    headers: { Authorization: `Bearer ${token}` },
                                    responseType: 'blob',
                                }
                            );
                            const imageUrl = URL.createObjectURL(imageResponse.data);
                            return { ...teammate, profileImageUrl: imageUrl };
                        } catch (error) {
                            console.error(`Error fetching image for ${teammate.nickName}:`, error);
                            return { ...teammate, profileImageUrl: placeholderImage };
                        }
                    } else {
                        return { ...teammate, profileImageUrl: placeholderImage };
                    }
                })
            );

            setTeammates(filteredTeammates);
        }
    })
    .catch(err => {
        console.error(err);
        setError("Error fetching player or team data.");
    })
    .finally(() => {
        setLoading(false); // Stop loading animation
        setLoadingLocal(false); // Ensure component's local loading state is updated
    });
    
}, [nickName, setLoading]);




  useEffect(() => {
    let currentBlobUrl = null; // Store the current blob URL

    if (profileImageName) {
        fetchProfileImage(profileImageName)
            .then(blobUrl => {
                if (currentBlobUrl) {
                    URL.revokeObjectURL(currentBlobUrl); // Revoke the previous blob URL
                }
                currentBlobUrl = blobUrl; // Update to the new blob URL
                setProfileImage(blobUrl); // Set the new profile image
            })
            .catch(() => {
                setProfileImage(placeholderImage); // Use placeholder on error
            });
    }

    return () => {
        if (currentBlobUrl) {
            URL.revokeObjectURL(currentBlobUrl); // Revoke blob URL when the component unmounts or profile changes
        }
    };
}, [profileImageName]);

    const fetchProfileImage = async (imageName) => {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error("No authorization token found. Please log in again.");
        throw new Error("Unauthorized");
    }

    try {
        const response = await axios.get(`http://localhost:8080/api/images/${imageName}`, {
            headers: { Authorization: `Bearer ${token}` },
            responseType: 'blob',
        });
        return URL.createObjectURL(response.data); // Return the blob URL
    } catch (err) {
        console.error("Error fetching profile image:", err);
        throw err;
    }
};

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;
  if (!player) return <div>No player data found</div>;

   const today = new Date();
  const pastEvents = player.events
    .filter(event => new Date(event.eventEndDate) < today)
    .sort((a, b) => new Date(b.eventEndDate) - new Date(a.eventEndDate));
  const ongoingEvents = player.events
    .filter(event => new Date(event.eventStartDate) <= today && new Date(event.eventEndDate) >= today)
    .sort((a, b) => new Date(b.eventStartDate) - new Date(a.eventStartDate));
  const upcomingEvents = player.events
    .filter(event => new Date(event.eventStartDate) > today)
    .sort((a, b) => new Date(a.eventStartDate) - new Date(b.eventStartDate));
  // Radar chart data
  const radarData = {
    labels: ['Aiming', 'Teamwork', 'Communication', 'Game Sense', 'Reflex', 'Strategy'],
    datasets: [
      {
        label: `${player.nickName}'s Skills`,
        data: [80, 70, 75, 85, 90, 65],
        backgroundColor: 'rgba(23, 162, 184, 0.2)',
        borderColor: '#17a2b8',
        borderWidth: 2,
        pointBackgroundColor: '#17a2b8',
        pointBorderColor: '#fff',
        pointBorderWidth: 2,
        pointRadius: 4,
        pointHoverRadius: 6,
      },
    ],
  };

  const radarOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      r: {
        beginAtZero: true,
        max: 100,
        ticks: { stepSize: 25, color: '#fff', backdropColor: 'rgba(0, 0, 0, 0)' },
        grid: { color: '#555' },
        angleLines: { color: '#555' },
      },
    },
    plugins: { legend: { labels: { color: '#fff' } } },
  };


  
  
 
  return (
    <div className="player-detail">
      <div className="player-header">
        <img src={profileImage || placeholderImage} alt={`${player.firstName} ${player.lastName}`} className="player-icon" />
        <div className="player-name">
          <h1>{player?.firstName} "<span>{player?.nickName}</span>" {player?.lastName}</h1>
          <img 
            src={`https://flagcdn.com/48x36/${player.countryCode.toLowerCase()}.png`} 
            alt={player.countryCode}
            className="flag-icon"
          />
        </div>
      </div>

      <hr className="divider" />

      <div className="chart-info-container">
        <div className="chart-placeholder">
          <Radar data={radarData} options={radarOptions} />
        </div>
        <div className="player-info-box">
          <p><strong>Ingame Role:</strong> {capitalize(player?.ingameRole)}</p>
          <p><strong>Date of Birth:</strong> {player?.dateOfBirth}</p>
          <p><strong>Gender:</strong> {player?.gender}</p>
          <p><strong>Team:</strong> {player?.teamName || 'No team'}</p>

          {/* Display past events */}
           <div className="events-section">
            <h3>Past Events</h3>
            {pastEvents?.length > 0 ? (
              <ul>
                {pastEvents?.map((event, index) => (
                  <li key={index}>{event?.eventName} ({event?.eventStartDate} - {event?.eventEndDate})</li>
                ))}
              </ul>
            ) : <p>No past events</p>}

            <h3>Ongoing Events</h3>
            {ongoingEvents?.length > 0 ? (
              <ul>
                {ongoingEvents?.map((event, index) => (
                  <li key={index}>{event?.eventName} ({event?.eventStartDate} - {event?.eventEndDate})</li>
                ))}
              </ul>
            ) : <p>No ongoing events</p>}

            <h3>Upcoming Events</h3>
            {upcomingEvents?.length > 0 ? (
              <ul>
                {upcomingEvents?.map((event, index) => (
                  <li key={index}>{event?.eventName} ({event?.eventStartDate} - {event?.eventEndDate})</li>
                ))}
              </ul>
            ) : <p>No upcoming events</p>}
          </div>
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
                          src={teammate.profileImageUrl || placeholderImage} 
                          alt={`${teammate.firstName} ${teammate.lastName}`}
                          className="teammate-image"
                      />
                      <div className="teammate-name">
                          {teammate.firstName} "{teammate.nickName}" {teammate.lastName}
                      </div>
                      <div className="teammate-role" style={{ color: teammate.ingameRole === 'COACH' ? '#FF6347' : '' }}>
                            {capitalize(teammate.ingameRole)}
                        </div>
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
