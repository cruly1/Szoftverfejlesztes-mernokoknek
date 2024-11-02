import React, { useEffect, useState } from 'react';
import TeamList from '../../components/Lists/TeamList/TeamList';
import axios from 'axios';
import './Teams.css';

function Teams() {
  const [teams, setTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isAddTeamModalOpen, setIsAddTeamModalOpen] = useState(false);
  const [newTeamName, setNewTeamName] = useState("");
   const token = localStorage.getItem('token');// Check if user is logged in

  useEffect(() => {
    const token = localStorage.getItem('token');
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
      });
  }, []);

  const handleAddTeamClick = () => {
    setIsAddTeamModalOpen(true);
  };

  const handleTeamNameChange = (e) => {
    setNewTeamName(e.target.value);
  };

  const handleAddTeamSubmit = (e) => {
    e.preventDefault();
    if (!newTeamName.trim()) {
      alert("Please enter a team name.");
      return;
    }

    axios.post("http://localhost:8080/api/teams/", { teamName: newTeamName }, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        setTeams([...teams, response.data]); // Update the teams list with the new team
        setIsAddTeamModalOpen(false); // Close modal
        setNewTeamName(""); // Clear the input
      })
      .catch(err => {
        console.error("Error adding team:", err);
        alert("Failed to add team. Please try again.");
      });
  };

  if (loading) return <div>Loading teams...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="teams-page">
      <h1>Teams</h1>

      {token && (
        <button onClick={handleAddTeamClick} className="add-team-button">Add Team</button>
      )}

      <div className="teams-list">
        {teams.map(team => (
          <TeamList key={team.id} team={team} />
        ))}
      </div>

      {isAddTeamModalOpen && (
        <div className="modal-overlay" onClick={() => setIsAddTeamModalOpen(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Add New Team</h2>
            <form onSubmit={handleAddTeamSubmit}>
              <input
                type="text"
                value={newTeamName}
                onChange={handleTeamNameChange}
                placeholder="Enter team name"
                required
              />
              <div className="modal-buttons">
                <button type="submit" className="save-button">Add Team</button>
                <button type="button" className="cancel-button" onClick={() => setIsAddTeamModalOpen(false)}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default Teams;
