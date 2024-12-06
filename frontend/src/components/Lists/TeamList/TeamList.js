import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import { capitalize } from '../../../utils/utils.js';
import axios from 'axios'; 
import './TeamList.css';
import { LoadingContext } from '../../../LoadingWrapper';

const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"; 

function TeamList({ team }) {
  const [teams, setTeams] = useState([]);
  const [loading, setLoadingLocal] = useState(true);
  const [error, setError] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedTeam, setSelectedTeam] = useState(null);
  const [playerTeamName, setPlayerTeamName] = useState(null);
  const [scrollingEvents, setScrollingEvents] = useState([]);
  const [playerImages, setPlayerImages] = useState({}); // Store player images
  const { setLoading } = useContext(LoadingContext);

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (!token) {
      setError('Unauthorized access. Please log in.');
      setLoadingLocal(false);
      return;
    }

    

    setLoading(true);

    axios.get("http://localhost:8080/api/teams/getAllTeams", {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        setTeams(response.data);
      })
      .catch(err => {
        console.error(err);
        setError("Error fetching teams.");
      })
      .finally(() => {
        setLoading(false);
        setLoadingLocal(false);
      });
  }, []);

  useEffect(() => {
    const nickName = localStorage.getItem('nickname');
    const token = localStorage.getItem('token');

    if (!nickName || !token) {
      return;
    }
    
    // Fetch player data to get the current teamName
    axios
      .get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setPlayerTeamName(response.data.teamName || null); // Fetch teamName or set to null
        
      })
      .catch((err) => {
        console.error('Error fetching player data:', err);
      });
      
  }, []);

  useEffect(() => {
    const events = team.events ? team.events : [];
    setScrollingEvents([...events, ...events]);
  }, [team.events]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setScrollingEvents(prevEvents => {
        const [firstEvent, ...rest] = prevEvents;
        return [...rest, firstEvent];
      });
    }, 3600000); 
    return () => clearInterval(intervalId); 
  }, [scrollingEvents]);

   const fetchPlayerImage = async (player) => {
    const token = localStorage.getItem('token');
    if (!token || !player.profileImageName) {
      return placeholderImage;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/api/images/${player.profileImageName}`,
        {
          headers: { Authorization: `Bearer ${token}` },
          responseType: 'blob',
        }
      );
      return URL.createObjectURL(response.data);
    } catch (err) {
      console.error(`Error fetching image for ${player.nickName}:`, err);
      return placeholderImage;
    }
  };

  const loadPlayerImages = async () => {
    const images = {};
    for (const player of team.players) {
      images[player.nickName] = await fetchPlayerImage(player);
    }
    setPlayerImages(images);
  };

  useEffect(() => {
    if (isOpen && team.players) {
      loadPlayerImages();
    }
  }, [isOpen, team.players]);

  const toggleDropdown = () => {
    const newState = !isOpen;
    setIsOpen(newState);
    localStorage.setItem(`dropdownState-${team.teamName}`, JSON.stringify(newState));
  };

  // Sort players to place "COACH" at the end
  const sortedPlayers = [...team.players].sort((a, b) => {
    if (a.ingameRole === "COACH") return 1;
    if (b.ingameRole === "COACH") return -1;
    return 0;
  });

  const openModal = (teamName) => {
    setSelectedTeam(teamName);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedTeam(null);
  };

   const handleConfirmJoin = async () => {
    const token = localStorage.getItem('token');
    const nickName = localStorage.getItem('nickname');

    if (!nickName || !token) {
        alert('Player not logged in or unauthorized access.');
        return;
    }

    const minLoadingTime = 2000; // Minimum duration for loading animation in milliseconds
    const startTime = Date.now(); // Record the start time

    setLoading(true);
    try {
        // Fetch the current player object
        const response = await axios.get(
            `http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );

        const playerData = response.data;

        // Update the player object with the new teamName
        const updatedPlayerData = {
          firstName: playerData.firstName,
          lastName: playerData.lastName,
          nickName: playerData.nickName,
          ingameRole: playerData.ingameRole,
          dateOfBirth: playerData.dateOfBirth,
          gender: playerData.gender,
          nationality: {
              countryName: playerData.countryName,
          },
          team :{
            teamName: selectedTeam,
          }, // Update only the teamName
          profileImageName: playerData.profileImageName,
          profileImageType: playerData.profileImageType,
        };
        console.log('Updated player data:', updatedPlayerData);
        // Send the updated player object in the PUT request
        await axios.put(
            `http://localhost:8080/api/players/updatePlayer/search?nickName=${nickName}`,
            updatedPlayerData,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                withCredentials: true,
            }
        );


        setPlayerTeamName(selectedTeam);
        
        window.location.reload(); // Reload the page
        
        closeModal(); 
    } catch (error) {
        console.error('Error updating player:', error);
        alert('Failed to join the team. Please try again.');
        
        closeModal(); 
    } finally {
        const elapsedTime = Date.now() - startTime;
        const remainingTime = Math.max(minLoadingTime - elapsedTime, 0);

        // Ensure loading lasts at least `minLoadingTime`
        setTimeout(() => {
            setLoading(false); // Stop loading animation
            closeModal();
        }, remainingTime);
    }
};
const handleLeaveTeam = async () => {
    const token = localStorage.getItem('token');
    const nickName = localStorage.getItem('nickname');

    if (!nickName || !token) {
      alert('Player not logged in or unauthorized access.');
      return;
    }
    
    const minLoadingTime = 2000; // Minimum duration for loading animation in milliseconds
    const startTime = Date.now();


    setLoading(true);

    try {
      const response = await axios.get(
        `http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      const playerData = response.data;
      console.log('Player data:', playerData);
      const updatedPlayerData = {
        firstName: playerData.firstName,
          lastName: playerData.lastName,
          nickName: playerData.nickName,
          ingameRole: playerData.ingameRole,
          dateOfBirth: playerData.dateOfBirth,
          gender: playerData.gender,
          nationality: {
              countryName: playerData.countryName,
          },
          profileImageName: playerData.profileImageName,
          profileImageType: playerData.profileImageType,
          teamName: null,
           // Update only the teamName
      };

      await axios.put(
        `http://localhost:8080/api/players/updatePlayer/search?nickName=${nickName}`,
        updatedPlayerData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        }
      );
      setPlayerTeamName(null);
      window.location.reload(); // Reload the page
       // Clear the current teamName in state
    } catch (error) {
      console.error('Error leaving team:', error);
      alert('Failed to leave the team. Please try again.');
    } finally {
      const elapsedTime = Date.now() - startTime;
        const remainingTime = Math.max(minLoadingTime - elapsedTime, 0);

        // Ensure loading lasts at least `minLoadingTime`
        setTimeout(() => {
          setLoading(false); // Stop loading animation
        }, remainingTime);
    }
  };
  if (loading) return <div>Loading teams...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="team-item">
      <div className="team-header">
        <button className="team-button" onClick={toggleDropdown}>
          {team.teamName} {isOpen ? '▲' : '▼'}
        </button>
         {playerTeamName === team.teamName ? (
    <button className="leave-team-button" onClick={handleLeaveTeam}>
      Leave Team
    </button>
  ) : playerTeamName === null ? (
    <button className="join-team-button" onClick={() => openModal(team.teamName)}>
      Join Team
    </button>
  ) : null}

        <div className="scrolling-events-container">
          <div className="scrolling-events">
            {scrollingEvents.length > 0 ? (
              scrollingEvents.map((event, index) => (
                event && event.eventName && event.eventDate ? ( // Check if event and properties exist
                  <span key={`${event.eventName}-${index}`} className="scrolling-event-text">
                    {event.eventName} ({event.eventDate})
                  </span>
                ) : null // If the event is undefined or lacks properties, render nothing
              ))
            ) : (
              <span className="no-event-text">No events available</span>
            )}
          </div>
        </div>
      </div>

      {isOpen && (
        <ul className="players-dropdown">
          {sortedPlayers.map(player => (
            <li key={player.nickName} className="player-item">
              <Link to={`/players/${player.nickName}`} className="team-player-link">
                <img 
                  src={playerImages[player.nickName] || placeholderImage} 
                  alt={`${player.firstName} ${player.lastName}`}
                  className="player-image"
                />
                <div className="player-nickname">{player.nickName}</div>
                <div className="player-role">
                  {player.ingameRole === "IGL" || player.ingameRole === "AWP" ? player.ingameRole : capitalize(player.ingameRole)}
                </div>
              </Link>
            </li>
          ))}
        </ul>
      )}

       {/* Modal for confirming the join action */}
      {isModalOpen && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Confirm Join Team</h2>
            <p>Are you sure you want to join <strong>{selectedTeam}</strong>?</p>
            <div className="modal-buttons">
              <button className="confirm-button" onClick={handleConfirmJoin}>
                Yes, I will join {selectedTeam}
              </button>
              <button className="cancel-button" onClick={closeModal}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default TeamList;
